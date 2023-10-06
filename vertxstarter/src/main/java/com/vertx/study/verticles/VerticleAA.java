package com.vertx.study.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class VerticleAA extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(VerticleAA.class);

  @Override
  public void start(Promise<Void> startPromise)  throws Exception {
    LOGGER.info("Verticle AA started working");
    startPromise.complete();
  }

  @Override
  public void stop(final Promise<Void> stopPromise) throws Exception {
    LOGGER.debug("Stop "+ this.getClass().getName().toString());
    stopPromise.complete();
  }
}
