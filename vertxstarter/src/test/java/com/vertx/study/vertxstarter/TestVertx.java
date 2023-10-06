package com.vertx.study.vertxstarter;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class TestVertx {

  private Vertx vertx;

  @BeforeEach
  public void setUp(VertxTestContext context) {
    vertx = Vertx.vertx();
    vertx.deployVerticle(MainVerticle.class.getName(),
      context.succeeding(id -> context.completeNow())
      );
  }
  @AfterEach
  public void tearDown(VertxTestContext context) {
    vertx.close(context.succeeding(id->context.completeNow()));
  }

  @Test
  public void testMyApplication(VertxTestContext context) {

  }


}
