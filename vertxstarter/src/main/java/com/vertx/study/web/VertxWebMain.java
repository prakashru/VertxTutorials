package com.vertx.study.web;


import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class VertxWebMain extends AbstractVerticle {

  public final static int PORT = 8888;


  public static void main(String[] args) {
    var vertx= Vertx.vertx();
    vertx.exceptionHandler(error->{
      System.out.println("Unhandled Error {} "+error);
    });
    vertx.deployVerticle(new VertxWebMain())
      .onFailure(err-> System.out.println("Failed to Deploy "+err))
      .onSuccess(id->{
        System.out.println("Deployed {} "+RestApiVerticles.class.getName()+ " with id {} "+id);
      });
  }

  public static void mainCopy(String[] args) {

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
    System.out.println("Test Vertx Web Main Verticle");
   vertx.deployVerticle(RestApiVerticles.class.getName(),
       new DeploymentOptions().setInstances(processor()))
     .onFailure(startPromise::fail)
     .onSuccess(id->{
       System.out.println("Deployed {} "+RestApiVerticles.class.getName()+ " with id {} "+id);
       startPromise.complete();
     });

  }

  private static int processor() {
    return Math.max(1, Runtime.getRuntime().availableProcessors()/2);
  }

  private void startRestApiVerticlesWithScalling(Promise<Void> startPromise) {
    System.out.println("Started Vertx Web");
    final Router routerApi= Router.router(vertx);
    routerApi
      .route()
      .handler(BodyHandler.create()
       // .setBodyLimit(1024)
       // .setHandleFileUploads(true)
      )
      .failureHandler(handleFailuer());
    AttachRouter.attach(routerApi);
    QueryApiRouter.attach(routerApi);
    WatchListRestApi.attach(routerApi);


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
      }).listen(PORT, http->{
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("Started at port 8888");
        } else {
          startPromise.fail(http.cause());
          System.out.println("Failed");
        }
      });
  }

  private static Handler<RoutingContext> handleFailuer() {
    return errorContext -> {
      if (errorContext.response().ended()) {
        return;
      }
      System.out.println("Route Error {}" + errorContext.failure());
      errorContext.response()
        .setStatusCode(500)
        .end(new JsonObject().put("error", "Error handling Custom Object").toBuffer());
    };
  }
}
