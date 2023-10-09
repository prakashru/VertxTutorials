package com.vertx.study.web;

import com.vertx.study.verticlecopy.VerticleN;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

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
    final Router routerApi= Router.router(vertx);
   routerApi.get("/assets").handler(context->{
     final JsonArray response=new JsonArray();
     response.add(new JsonObject().put("symbol", "AAPL"))
       .add(new JsonObject().put("symbol","AMZN"))
       .add(new JsonObject().put("symbol", "NFLX"));
     System.out.println("Response {} "+response);
     System.out.println("Response {} "+context.normalizedPath());
     context.response().end(response.toBuffer());
   });

//    vertx.createHttpServer()
//      .requestHandler(req->{
//      req.response()
//        .putHeader("context-type", "text/plain")
//        .end("Hello World!");
//    }).listen(8888, http->{
//      if(http.succeeded()){
//        startPromise.complete();
//        System.out.println("HTTP server started on port 8888");
//      } else {
//        System.out.println("HTTP server failed to start on port 8888");
//        startPromise.fail(http.cause());
//      }
//    });
    vertx.createHttpServer()
      .requestHandler(routerApi)
      .exceptionHandler(error->{
        System.out.println("Error "+ error);
      }).listen(8888 , http->{
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("Started at port 8888");
        } else {
          startPromise.fail(http.cause());
          System.out.println("Failed");
        }
      });

  }
}
