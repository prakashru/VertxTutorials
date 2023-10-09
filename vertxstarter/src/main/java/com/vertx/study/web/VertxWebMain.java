package com.vertx.study.web;

import com.vertx.study.verticlecopy.VerticleN;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.UUID;

public class VertxWebMain extends AbstractVerticle {

  public static void main(String[] args) {

    var vertx= Vertx.vertx();
    vertx.exceptionHandler(error->{
      System.out.println("Unhandled Error {} "+error);
    });
    vertx.deployVerticle(new VertxWebMain(), ar->{
      if(ar.failed()){
        System.out.println("Failed to start a service {} "+ar.cause());
        return;
      }
      System.out.println("Main class");
    });

  }

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    System.out.println("Started Vertx Web");
//    vertx.deployVerticle(VertxWebMain.class.getName(),
//      new DeploymentOptions()
//        .setInstances(4)
//        .setConfig(new JsonObject()
//          .put("id", UUID.randomUUID().toString())
//          .put("name", VerticleN.class.getSimpleName())
//        )
//    );
//    startPromise.complete();
    vertx.createHttpServer().requestHandler(req->{
      req.response()
        .putHeader("context-type", "text/plain")
        .end("Hello World!");
    }).listen(8888, http->{
      if(http.succeeded()){
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        System.out.println("HTTP server failed to start on port 8888");
        startPromise.fail(http.cause());
      }
    });

  }
}
