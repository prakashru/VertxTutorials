package com.vertx.study.web;

import com.vertx.study.verticlecopy.MainVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestAssetsRestApi {

  @BeforeEach
  void verticle_deployed(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(),
      testContext.succeeding(id->{testContext.completeNow();}));
  }
  @Test
  void verticle_all_assets(Vertx vertx, VertxTestContext testContext) throws Throwable {
    var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(VertxWebMain.PORT));
    client.get("/assets")
      .send()
      .onComplete(testContext.succeeding(response->{
        var json=response.bodyAsJsonArray();
        System.out.println("response {} "+json);
        assertEquals("[{\"symbol\":\"symbol\",\"code\":\"PPPP\"},{\"symbol\":\"symbol\",\"code\":\"AAAA\"},{\"symbol\":\"symbol\",\"code\":\"BBBB\"}]", json.encode());
        assertEquals(200, response.statusCode());
        testContext.completeNow();
      }));
  }
}
