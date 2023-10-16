package com.vertx.study.web;

import com.vertx.study.web.pojo.Asset;
import com.vertx.study.web.pojo.Assets;
import io.vertx.core.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchList {

  public List<Asset> assets;

  JsonObject toJsonObject(){
    return JsonObject.mapFrom(this);
  }

}
