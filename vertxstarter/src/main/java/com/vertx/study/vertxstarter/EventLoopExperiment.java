package com.vertx.study.vertxstarter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.impl.VertxThread;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class EventLoopExperiment extends AbstractVerticle {
  private final static Logger logger= LoggerFactory.getLogger(EventLoopExample.class);

  private final Map<String, AtomicInteger> threadCounts;

  EventLoopExperiment(Map<String, AtomicInteger> threadCounts) {
    this.threadCounts = threadCounts;
  }
  public static void main(String[] args) throws InterruptedException {

    logger.info("Thread Count one "+ Thread.activeCount());
//    Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//    for(Thread t: threadSet) {
//      logger.info( "one:---->"+t.getName());
//    }
    Vertx vertx1= Vertx.vertx();
    logger.info("Thread Count two "+ Thread.activeCount());
//    Set<Thread> threadSet1 = Thread.getAllStackTraces().keySet();
//    for(Thread t: threadSet1){
//     logger.info( "two:---->"+t.getName());
//    }

    final Map<String, AtomicInteger> threadCounts = new ConcurrentHashMap<>();
    int verticles=100;

    final CountDownLatch latch = new CountDownLatch(verticles);
    for(int i=0;i<verticles;i++) {
      vertx1.deployVerticle(new EventLoopExperiment(threadCounts),
        c -> latch.countDown());
    }
    latch.await();
    logger.info("After deploying 1000 verticles "+Thread.activeCount());

//    So, some simple math here. We had 3 threads before, and now, 16 additional threads were added. They’re all named in the form vert.x-eventloop-thread-X. You can start ten thousand verticles, and the amount of your event loop threads won’t be affected.
//
//      So, two important takeaways until now:
//
//    Vert.x is not single threaded
//    Maximum number of Event Loop threads depends on number of CPUs, not on number of verticles deployed

    // /**
    //   * The default number of event loop threads to be used  = 2 * number of cores on the machine
    //   */
    //  public static final int DEFAULT_EVENT_LOOP_POOL_SIZE = 2 * CpuCoreSensor.availableProcessors();

    //https://github.com/eclipse-vertx/vert.x/blob/master/src/main/java/io/vertx/core/VertxOptions.java#L38



  }

  @Override
  public void start() throws Exception {
    //logger.info(Thread.currentThread().getName());
   threadCounts.computeIfAbsent(Thread.currentThread().getName(), t->new AtomicInteger(0)).incrementAndGet();
    System.out.println(Thread.currentThread().getName()+"===="+threadCounts);
  }
}
