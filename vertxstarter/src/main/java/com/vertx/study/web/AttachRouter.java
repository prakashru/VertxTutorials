package com.vertx.study.web;

import com.vertx.study.web.pojo.Assets;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class AttachRouter {

//  public static void attach(Router parent) {
//    parent.get("/assets").handler(context -> {
//      final JsonArray response = new JsonArray();
//      response.add(new JsonObject().put("symbol", "AAPL"))
//        .add(new JsonObject().put("symbol", "AMZN"))
//        .add(new JsonObject().put("symbol", "NFLX"));
//      System.out.println("Response {} " + response);
//      System.out.println("Response {} " + context.normalizedPath());
//      context.response().end(response.toBuffer());
//    });
//
//  }

  public static void attach(Router parent) {
    parent.get("/assets").handler(context -> {
      final JsonArray response = new JsonArray();
      response
        .add(new Assets("symbol", "PPPP"))
        .add(new Assets("symbol", "AAAA"))
        .add(new Assets("symbol", "BBBB"));
      System.out.println("Response {} " + response);
      System.out.println("Response {} " + context.normalizedPath());
      context.response().end(response.toBuffer());
    });

  }
}
