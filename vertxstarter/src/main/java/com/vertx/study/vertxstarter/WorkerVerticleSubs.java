package com.vertx.study.vertxstarter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class WorkerVerticleSubs extends AbstractVerticle {

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    System.out.println("Deploying Worker verticles "+ Thread.currentThread().getName());
    startPromise.complete();
    Thread.sleep(5000);
    System.out.println("Blocking Operarion Done");
  }
}
