package com.vertx.study.web.pojo;

public class Assets {

  private String symbol;
  private String code;

  public Assets() {

  }

  public Assets(String symbol, String code) {
    this.symbol = symbol;
    this.code = code;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
