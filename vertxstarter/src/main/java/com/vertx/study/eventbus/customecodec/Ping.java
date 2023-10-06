package com.vertx.study.eventbus.customecodec;

public class Ping {
  private Integer id;
  private boolean test;

  public Ping(){}
  public Ping(Integer id, boolean test) {
    this.id = id;
    this.test = test;
  }

  public Integer getId() {
    return id;
  }

  public boolean isTest() {
    return test;
  }


  @Override
  public String toString() {
    return "Ping{" +
      "id=" + id +
      ", test=" + test +
      '}';
  }
}
