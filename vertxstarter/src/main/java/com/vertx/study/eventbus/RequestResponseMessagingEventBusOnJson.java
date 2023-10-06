package com.vertx.study.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class RequestResponseMessagingEventBusOnJson {

  public static void main(String[] args) {

    var vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());

  }

  public static class RequestVerticle extends AbstractVerticle {
    static final String ADDRESS = "my.request.address";
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      //var vertx= Vertx.vertx();
      //String message="Hello Another Verticle";
      final var message = new JsonObject().put("id", 1).put("version", 2).put("message","VertxEventBus");
      System.out.println("Sending message: "+message);
      var eventBus= vertx.eventBus();
      eventBus.<JsonArray>request(ADDRESS, message, reply->{
        System.out.println("Response {} "+reply.result().body());
      });
    }
  }

  public static class ResponseVerticle extends AbstractVerticle {
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus=vertx.eventBus();
      eventBus.<JsonObject>consumer(RequestVerticle.ADDRESS, message -> {
          System.out.println("Received the message :"+message.body());
          message.reply(new JsonArray().add("one").add("two").add("three"));
      });
    }
  }
}
