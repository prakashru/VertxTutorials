package com.vertx.study.vertxstarter;

import com.vertx.study.Person;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonObjectTest {

  @Test
  void jsonObjectMapped() {

    final JsonObject jsonObject=new JsonObject();
    jsonObject.put("id", 23);
    jsonObject.put("name","prakash");
    String encodedString = jsonObject.encode();
    assertEquals("{\"id\":23,\"name\":\"prakash\"}", encodedString);

    final JsonObject decodedJsonObject = new JsonObject(encodedString);
    assertEquals(jsonObject, decodedJsonObject);
  }

  @Test
  void jsonObjectAsMap() {
    Map<String, Object> myMap=new HashMap<>();
    myMap.put("id", 23);
    myMap.put("name","prakash");
    myMap.put("vertx_loving", true);

   final JsonObject jsonObject= new JsonObject(myMap);
   assertEquals(myMap, jsonObject.getMap());
    assertEquals(23, jsonObject.getInteger("id"));
    assertEquals(true, jsonObject.getBoolean("vertx_loving"));


    JsonArray myJsonArray =  new JsonArray();
    myJsonArray
      .add(new JsonObject().put("id", 1))
      .add(new JsonObject().put("id", 2))
      .add(new JsonObject().put("id", 3))
      ;
    assertEquals("[{\"id\":1},{\"id\":2},{\"id\":3}]", myJsonArray.encode());
  }

  @Test
  void canMapJavaObjects() {
    final Person person = new Person(1, "Alice", true);

    final JsonObject alice = JsonObject.mapFrom(person);
    assertEquals(person.getId(), alice.getInteger("id"));
    assertEquals(person.getName(), alice.getString("name"));
    assertEquals(person.isLovesVertx(), alice.getBoolean("lovesVertx"));

    final Person person2 = alice.mapTo(Person.class);
    assertEquals(person.getId(), person2.getId());
    assertEquals(person.getName(), person2.getName());
    assertEquals(person.isLovesVertx(), person2.isLovesVertx());
  }

}
