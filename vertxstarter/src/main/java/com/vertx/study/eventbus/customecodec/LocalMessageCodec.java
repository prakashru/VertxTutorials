package com.vertx.study.eventbus.customecodec;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public class LocalMessageCodec<T> implements MessageCodec<T,T> {

  private final String typeName;

  public LocalMessageCodec(Class<T> typeName) {
    this.typeName = typeName.getName();
  }

  @Override
  public void encodeToWire(final Buffer buffer, final T t) {
    throw new UnsupportedOperationException("Only local encode is supported.");
  }

  @Override
  public T decodeFromWire(int i, Buffer buffer) {
    throw new UnsupportedOperationException("Only local encode is supported.");
  }

  @Override
  public T transform(final T t) {
    return t;
  }

  @Override
  public String name() {
    return this.typeName;
  }

  @Override
  public byte systemCodecID() {
    return -1;
  }
}
