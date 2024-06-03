package com.vertx.study.eventbus.customecodec;

import io.vertx.core.*;

public class PingPongCustomeCodec {
  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new PingVerticle(), logError());
    vertx.deployVerticle(new PongVerticle(), logError());

  }

  private static Handler<AsyncResult<String>> logError() {
    return ar -> {
      System.out.println("error: " + ar.cause());
    };
  }

  public static class PingVerticle extends AbstractVerticle {
    static final String ADDRESS = PingVerticle.class.getName();
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {

      var eventBus=vertx.eventBus();
      final Ping message=new Ping(1010101, true);
      System.out.println("Sending message "+ message);
      eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
      eventBus.<Pong>request(ADDRESS, message, reply->{

        if(reply.failed()){
          System.out.println("Failed "+reply.cause());
          return;
        }
        System.out.println("Received message: "+reply.result().body());
      });
      startPromise.complete();
    }
  }
  public static class PongVerticle extends AbstractVerticle {
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
        vertx.eventBus().registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
        vertx.eventBus().<Ping>consumer(PingVerticle.ADDRESS, message -> {
          System.out.println("Received message from Ping "+message.body());
          message.reply(new Pong(100));
        }).exceptionHandler(error->{
          System.out.println("Error from Pong "+error);
        });
      startPromise.complete();
    }
  }
}
