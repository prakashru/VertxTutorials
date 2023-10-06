package com.vertx.study.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class RequestResponseMessagingEventBus {

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
      String message="Hello Another Verticle";
      System.out.println("Sending message: "+message);
      var eventBus= vertx.eventBus();
      eventBus.<String>request(ADDRESS, message, reply->{
        System.out.println("Response {} "+reply.result().body());
      });
    }
  }

  public static class ResponseVerticle extends AbstractVerticle {
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus=vertx.eventBus();
      eventBus.<String>consumer(RequestVerticle.ADDRESS, message -> {
          System.out.println("Received the message :"+message.body());
          message.reply("Received your message. Thanks!");
      });
    }
  }
}
