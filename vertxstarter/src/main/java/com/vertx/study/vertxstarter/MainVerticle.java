package com.vertx.study.vertxstarter;



import com.vertx.study.verticles.VerticleA;
import com.vertx.study.verticles.VerticleB;
import com.vertx.study.verticles.VerticleN;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.core.json.JsonObject;

import java.util.Random;
import java.util.UUID;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);


  public static void main(String[] args) {
    Vertx vertx1 = Vertx.vertx();
    vertx1.deployVerticle(new MainVerticle());
  }

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
    LOGGER.info("Starting Main Verticles "+ this.getClass().getName());
//    vertx.deployVerticle(new VerticleA());
//    vertx.deployVerticle(new VerticleB());
//    vertx.deployVerticle(VerticleN.class.getName(),
//      new DeploymentOptions()
//        .setInstances(4)
//        .setConfig(new JsonObject()
//          .put("id", UUID.randomUUID().toString())
//          .put("name", VerticleN.class.getSimpleName())
//        )
//    );
//    startPromise.complete();

    vertx.createHttpServer().requestHandler(req -> {
      int requestId = new Random().nextInt();
      System.out.println("Request ID :" + requestId);
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello from Vert.x!");
    }).listen(8888, http -> {
      if (http.succeeded()) {
        vertx.deployVerticle(new VerticleA());
        vertx.deployVerticle(new VerticleB());

        vertx.deployVerticle(VerticleN.class.getName(),
          new DeploymentOptions().setInstances(4)
            .setConfig(new JsonObject()
              .put("id", UUID.randomUUID().toString())
              .put("name", VerticleN.class.getSimpleName())
            )
        );
        startPromise.complete();
       LOGGER.info("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());

      }
    });
  }
}
