package com.vertx.study.web;

import com.vertx.study.verticlecopy.MainVerticle;
import com.vertx.study.web.pojo.Asset;
import com.vertx.study.web.pojo.Assets;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(VertxExtension.class)
public class TestWatchListRestApi {
  @BeforeEach
  void verticle_deployed(Vertx vertx, VertxTestContext testContext) {
    vertx.deployVerticle(new MainVerticle(),
      testContext.succeeding(id->{testContext.completeNow();}));
  }

  @Test
  void add_and_return_assets_from_watchlist(Vertx vertx, VertxTestContext testContext) throws Throwable {

    var client = WebClient.create(vertx, new WebClientOptions().setDefaultPort(VertxWebMain.PORT));
    var accountId= UUID.randomUUID();

    client.put("/account/watchlist/"+accountId.toString())
      .sendJsonObject(body())
      .onComplete(testContext.succeeding(response->{
        var json=response.bodyAsJsonObject();
        System.out.println("response PUT {} "+json);
        assertEquals("{\"assets\":[{\"name\":\"AMZN\"},{\"name\":\"TSLA\"}]}", json.encode());
        assertEquals(200, response.statusCode());
        assertEquals(HttpHeaderValues.APPLICATION_JSON.toString(),
          response.getHeader(HttpHeaders.CONTENT_TYPE.toString()));
        assertEquals("my-header-value", response.getHeader("my-header"));
        testContext.completeNow();
      })).compose(next->{
        client.get("/account/watchlist/"+accountId.toString())
          .send()
          .onComplete(testContext.succeeding(response->{
            var json=response.bodyAsJsonArray();
            System.out.println("response GET {} "+json);
            assertEquals("", json.encode());
            assertEquals(200, response.statusCode());
            testContext.completeNow();
          }));
        return Future.succeededFuture();
      });
  }







  private JsonObject body() {
    //return new WatchList(Arrays.asList(new Assets("AMZM" ), new Assets("TSLM"))).toJsonObject();
    return new WatchList(Arrays.asList(
      new Asset("AMZN"),
      new Asset("TSLA"))
    ).toJsonObject();
  }
}
