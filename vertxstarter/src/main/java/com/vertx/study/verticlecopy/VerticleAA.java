package com.vertx.study.verticlecopy;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleAA extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(VerticleAA.class);
 // private static final Logger LOG = LoggerFactory.getLogger(VerticleA.class);

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOG.debug("Start {}"+ VerticleAA.class.getClass().getName());
    startPromise.complete();
  }

  @Override
  public void stop(final Promise<Void> stopPromise) throws Exception {
    LOG.debug("Stop {}"+ VerticleAA.class.getClass().getName());
    stopPromise.complete();
  }
}
