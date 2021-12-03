package com.demoshop.shopping.domain.order;

import lombok.Value;

@Value(staticConstructor = "of")
public class CustomerId {
  String id;

  @Override
  public String toString() {
    return id;
  }
}
