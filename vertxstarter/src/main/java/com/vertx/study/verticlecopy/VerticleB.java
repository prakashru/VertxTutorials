package com.vertx.study.verticlecopy;

import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleB extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(VerticleB.class);


  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOG.debug("Start {}"+ VerticleB.class.getClass().getName());
    startPromise.complete();
  }
}
