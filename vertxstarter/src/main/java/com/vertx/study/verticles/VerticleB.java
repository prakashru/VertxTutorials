package com.vertx.study.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class VerticleB extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(VerticleB.class);

  @Override
  public void start(Promise<Void> startPromise)  throws Exception {
    LOGGER.info("Verticle B started working");
    startPromise.complete();
  }

}
