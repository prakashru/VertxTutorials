package com.vertx.study.web;

import com.vertx.study.web.pojo.Assets;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;


public class QueryApiRouter {

  public static void attach(Router router) {
    router.get("/quotes/:asset").handler(context ->{
      final String assetParam=context.pathParam("asset");
      //final String codeParam=context.pathParam("code");
      System.out.println("Assets Params {} "+assetParam);

      var mayBeQuote = Optional.ofNullable(assetParam);
      if(mayBeQuote.isEmpty()){
        context.response()
          .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
          .end(new JsonObject()
            .put("message", "quote for assets "+assetParam+" not available")
            .put("path", context.normalizedPath())
          .toBuffer());
        return;
      }

      //final Quote quote=new Quote();


      //Builder Object
      var quote= getBuild(assetParam, "aaaa");

      final JsonObject response=new JsonObject();
      System.out.println("Path {} responds with {} "+context.normalizedPath()+response.encode());
      context.response().end(response.toBuffer());
    });

  }

  private static Quote getBuild(String assetParam, String codeParam) {
    return Quote.builder()
      .assets(new Assets(assetParam))
      .valumn(randomValue())
      .ask(randomValue())
      .bid(randomValue())
      .lastPrice(randomValue())
      .build();
  }

  private static BigDecimal randomValue() {
    return BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(1,100));
  }
}
