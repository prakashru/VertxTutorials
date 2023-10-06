package com.vertx.study.verticlecopy;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleA extends AbstractVerticle {

  //private static final Logger LOG = LoggerFactory.getLogger(VerticleA.class);
  private static final Logger LOG = LoggerFactory.getLogger(VerticleA.class);

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOG.debug("Start {}"+ VerticleA.class.getClass().getName());
    vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
      LOG.debug("Deployed {}"+ VerticleAA.class.getName());
      vertx.undeploy(whenDeployed.result());
    });
    vertx.deployVerticle(new VerticleAB(), whenDeployed -> {
      LOG.debug("Deployed {}"+ VerticleAB.class.getName());
      // Do not undeploy
    });
    startPromise.complete();
  }
}
