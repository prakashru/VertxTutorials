package com.vertx.study.vertxstarter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class WorkerVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new WorkerVerticle());
  }

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(new WorkerVerticleSubs(),
      new DeploymentOptions()
        .setWorker(true)
        .setWorkerPoolSize(1)
        .setWorkerPoolName("worker-verticle")
    );
    startPromise.complete();
    executeBlockingCode();
  }

  public void executeBlockingCode() {
    vertx.executeBlocking(event-> {
      System.out.println("Starting blocking code");
      try{
        Thread.sleep(5000);
        event.complete();
      }catch(InterruptedException ex) {
        System.err.println(("Failed the event "));
        event.fail(ex);
      }
    }, result->{
      if(result.succeeded()) {
        System.out.println("Blocking Code is done "+Thread.currentThread().getName());

      } else {
        System.out.println("Blocking code failed "+result.cause());
      }
    });
  }

//  @Override
//  public void start(final Promise<Void> startPromise) throws Exception {
//    startPromise.complete();
//    vertx.executeBlocking(event->{
//      System.out.println("Executing Blocking Code");
//      try {
//        Thread.sleep(5000);
//        //event.complete();
//        event.fail("Force Faile!");
//      }catch(InterruptedException ex) {
//        System.out.println("Error: ");
//        ex.printStackTrace();
//        event.fail(ex);
//      }
//    }, result->{
//      if(result.succeeded()){
//        System.out.println("Blocking call done");
//      } else {
//        System.out.println("Blocking call failed"+ result.cause());
//      }
//
//    });
//  }
}
