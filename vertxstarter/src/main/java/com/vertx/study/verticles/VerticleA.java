package com.vertx.study.verticles;

import com.vertx.study.vertxstarter.MainVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class VerticleA extends AbstractVerticle {
  private static final Logger LOGGER = LoggerFactory.getLogger(VerticleA.class);

  @Override
  public void start(Promise<Void> startPromise)  throws Exception {
    LOGGER.info("Verticle A started working");
    vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
      LOGGER.info("undeploye Verticle AA");
      vertx.undeploy(whenDeployed.result());
    });
    vertx.deployVerticle(new VerticleAA(), whenDeployed -> {
      LOGGER.debug("Deployed {} "+ VerticleAA.class.getName());
      // Do not undeploy
    });

    startPromise.complete();
  }
}
