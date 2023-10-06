package com.vertx.study.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class VerticleN extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(VerticleN.class);

  @Override
  public void start(Promise<Void> startPromise)  throws Exception {
    LOGGER.info("Verticle N started working. Current Thread Name "+Thread.currentThread().getName());
    vertx.deployVerticle(new VerticleN(), whenDeployed -> {
      LOGGER.info("undeploye Verticle N");
      //vertx.undeploy(whenDeployed.result());
    });
    startPromise.complete();
  }

}
