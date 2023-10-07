package com.vertx.study.vertxstarter;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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

}
