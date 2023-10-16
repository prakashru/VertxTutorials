package com.vertx.study.web;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class WatchListRestApi {

  public static void attach(final Router parent){

    final HashMap<UUID, WatchList> watchListPerAccount=new HashMap<>();

   final  String path="/account/watchlist/:accountId";

    parent.get("/account/watchlist/:accountId").handler((context->{
      final var accountId = getAccountId(context);
      var watchList= Optional.of(watchListPerAccount.get(UUID.fromString(accountId)));
      if(watchList.isEmpty()) {
        context.response()
          .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON.toString())
          .putHeader("my-header","my-header-value")
          .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
          .end(new JsonObject()
            .put("message", "watchList for account "+accountId+ " not found")
            .put("path", context.normalizedPath())
            .toBuffer()
          );
        return;
      }
      context.response().end(watchList.get().toJsonObject().toBuffer());
    }));
    parent.put("/account/watchlist/:accountId").handler(context->{
      final var accountId = getAccountId(context);
      var json=context.getBodyAsJson();
      var watchlist=json.mapTo(WatchList.class);
      watchListPerAccount.put(UUID.fromString(accountId), watchlist);
      context.response()
        .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON.toString())
        .putHeader("my-header","my-header-value")
        .end(json.toBuffer());

    });
    parent.delete(path).handler(context->{
      final var accountId=getAccountId(context);
      final WatchList deleted=watchListPerAccount.remove(UUID.fromString(accountId.toString()));
      context.response()
        .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
        .setStatusCode(HttpResponseStatus.OK.code())
        .end(deleted.toJsonObject().toBuffer());
    });
  }

  private static String getAccountId(RoutingContext context) {
    var accountId = context.pathParam("accountId");
    System.out.println("Path Param {} accountId " + accountId);
    return accountId;
  }
}
