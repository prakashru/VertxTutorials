package com.vertx.study.web;

import com.vertx.study.web.pojo.Assets;
import io.vertx.core.json.JsonObject;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Quote {

  Assets assets;
  BigDecimal ask;
  BigDecimal bid;
  BigDecimal lastPrice;
  BigDecimal valumn;


  public JsonObject toJsonObject() {
    return JsonObject.mapFrom(this);
  }
}
