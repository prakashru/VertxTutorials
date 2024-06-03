package com.vertx.study.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;

import java.time.Duration;

public class PublisherSubscriverEventBus {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Publisher());
    vertx.deployVerticle(new SubscriberOne());
    vertx.deployVerticle(new SubscriberTwo());
    vertx.deployVerticle(SubscriberTwo.class.getName(),
      new DeploymentOptions().setInstances(2));
  }
  public static class Publisher extends AbstractVerticle {
    static final String ADDRESS = "my.request.address";
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      String message ="Publishing a new message";
      System.out.println("Publishing message" + message);
      vertx.setPeriodic(Duration.ofSeconds(10).toMillis(), id->{
        vertx.eventBus().publish(ADDRESS, message);
      });

    }
  }
  public static class SubscriberOne extends AbstractVerticle {
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      vertx.eventBus().<String>consumer(Publisher.ADDRESS, message->{
        System.out.println("Received SubscriverOne "+ message.body());
      });
      startPromise.complete();
    }
  }

  public static class SubscriberTwo extends AbstractVerticle {
    @Override
    public void start(final Promise<Void> startPromise) throws Exception {
      vertx.eventBus().<String>consumer(Publisher.ADDRESS, message->{
        System.out.println("Received SubscriberTwo "+message.body());
      });
      startPromise.complete();
    }
  }
}
