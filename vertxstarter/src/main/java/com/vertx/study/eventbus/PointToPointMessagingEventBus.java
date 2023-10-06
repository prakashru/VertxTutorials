package com.vertx.study.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class PointToPointMessagingEventBus {

  public static void main(String[] args) {
    var vertx= Vertx.vertx();
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(new Receiver());
  }
  public static class Sender extends AbstractVerticle {

    static final String ADDRESS = "my.request.address";
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      String message="Sending a new message";
      System.out.println("Sending message: "+message);
      vertx.setPeriodic(500, id->{
        vertx.eventBus().send(ADDRESS, message+String.valueOf(id));
      });
    }
  }

  public static class Receiver extends AbstractVerticle {
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(Sender.ADDRESS, reply->{
        System.out.println("Received message : "+ reply.body());
      });
    }
  }


}
