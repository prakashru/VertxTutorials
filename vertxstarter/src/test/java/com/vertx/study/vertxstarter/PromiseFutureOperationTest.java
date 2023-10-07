package com.vertx.study.vertxstarter;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.stream.BaseStream;

@ExtendWith(VertxExtension.class)
public class PromiseFutureOperationTest {

  @Test
  void promise_success(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    System.out.println("Started Promise");
    vertx.setTimer(500, id->{
      System.out.println("Receiving Promise ");
      promise.complete("Success!");
      context.completeNow();
    });

    System.out.println("Finished");
  }
  @Test
  void promise_failure(Vertx vertx, VertxTestContext context){
    final Promise<String> promise = Promise.promise();
    System.out.println("Started Promise");
    vertx.setTimer(500, id->{
      System.out.println("Failing Promise ");
      promise.fail(new RuntimeException("Failed!"));
      System.out.println("Failed...");
      context.completeNow();
    });
    System.out.println("Finished");
  }

  @Test
  void future_success(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise= Promise.promise();
    System.out.println("Future started");
    vertx.setTimer(500, id->{
      promise.complete("Complete the Operation");
      System.out.println("Promise done");
      context.completeNow();
    });
   final Future<String> future=promise.future();
   future.onSuccess(result->{
     System.out.println("End " +result);
     context.completeNow();
   }).onFailure(context::failNow);
  }

  @Test
  void future_failure(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    System.out.println("Start");
    vertx.setTimer(500, id -> {
      promise.fail(new RuntimeException("Failed!"));
      System.out.println("Timer done.");
    });
    final Future<String> future = promise.future();
    future
      .onSuccess(context::failNow)
      .onFailure(error -> {
        System.out.println("Result: "+error);
        context.completeNow();
      });
  }


  @Test
  void future_map(Vertx vertx, VertxTestContext context) {
    final Promise<String> promise = Promise.promise();
    System.out.println("Started Promise");
    vertx.setTimer(500, id->{
      System.out.println("Receiving Promise ");
      promise.complete("Success!");
      context.completeNow();
    });
    final Future<String> future=promise.future();
    future
      .map(asString->{
        System.out.println("Map String to JsonObject");
        return new JsonObject().put("key","Json Object!!!");
      })
        .map(jsonObject->new JsonArray().add(jsonObject))
        .onSuccess(result->{
            System.out.println("Result {} "+result+" Type "+result.getClass().getName());
            context.completeNow();
          })
        .onFailure(context::failNow);

    System.out.println("Finished");

  }

  @Test
  void future_coordination(Vertx vertx, VertxTestContext context) {
    vertx.createHttpServer()
      .requestHandler(request-> System.out.println(request))
      .listen(8080)
      .onFailure(context::failNow)
      .onSuccess(server->{
        System.out.println("Server Started on "+server.actualPort());
        context.completeNow();
      });
  }

  @Test
  void future_coordinationwithFuture(Vertx vertx, VertxTestContext context) {

    vertx.createHttpServer()
      .requestHandler(request-> System.out.println(request))
      .listen(10000)
      .compose(server->{
        System.out.println("Pass more info");
        return Future.succeededFuture(server);
      })
      .compose(server1->{
        System.out.println("even more info");
        return Future.succeededFuture(server1);
      })
      .onFailure(context::failNow)
      .onSuccess(server2->{
        System.out.println("Server Started on "+server2.actualPort());
        context.completeNow();
      });
  }


  @Test
  void future_composite(Vertx vertx, VertxTestContext context) {
    var one = Promise.<Void>promise();
    var two = Promise.<Void>promise();
    var three = Promise.<Void>promise();

    var futureOne = one.future();
    var futuretwo = two.future();
    var futurethree = three.future();

    CompositeFuture.all(futureOne, futuretwo, futurethree)
      .compose(oneserver->{
        System.out.println("Passing first info");
        return Future.succeededFuture(oneserver);
      })
      .onFailure(error->{
        System.out.println("Error: "+error);
        context.completeNow();
      })
      .onSuccess(result->{
        System.out.println("Result {} "+ result);
        context.completeNow();
      });

    vertx.setTimer(500, id->{
      System.out.println("One starting!!!");
      one.complete();
      System.out.println("two starting!!!");
      two.fail("Two failed");
      System.out.println("three starting!!!");
      //three.fail("Failed");
      three.complete();
    });
//https://betterprogramming.pub/understanding-vert-x-compose-vs-map-d464dee78980

  }


}
