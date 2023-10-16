package com.vertx.study.vertxstarter;

import io.vertx.core.*;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EventLoopExample extends AbstractVerticle {

  //Event Loop concept
//  while(queue.waitingForMessage()){
//    queue.processNextMessage();
//  }
//  //Event loop are used to implement
//    a) asynchronous programming models->Asynchronous function s are put into the queue
//    b) inter-process communication
//
//    Event loop works by
//    1) making a requent to some internal or external event provider(that generally blocks the request until an event has arrived)
//    2) then the calls the relavent event handler("dispatch the event")
  private final static Logger logger= LoggerFactory.getLogger(EventLoopExample.class);
  public static void main(String[] args) {
    logger.info("Active thread 1:"+Thread.activeCount());
    var vertx = Vertx.vertx(
      new VertxOptions()
        .setMaxEventLoopExecuteTime(500)
        .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
        .setBlockedThreadCheckInterval(1)
        .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
        .setEventLoopPoolSize(2)
    );
    vertx.deployVerticle(EventLoopExample.class.getName(),
      new DeploymentOptions().setInstances(40)
    );
    //logger.info("Active thread 2:"+Thread.activeCount());
  }

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
   System.out.println("Start {} " + getClass().getName() + " Thread "+Thread.currentThread().getName());
    //logger.info("Active thread 3:"+Thread.activeCount());
    startPromise.complete();
    // Do not do this inside a verticle
    Thread.sleep(5000);
  }
}
