package com.demoshop.shopping.domain.order;

import java.util.UUID;
import lombok.Value;

@Value(staticConstructor = "of")
public class OrderId {
  String id;

  public static OrderId from(CustomerId customerId) {
    return OrderId.of(String.format("%s-%s", customerId, UUID.randomUUID()));
  }

  @Override
  public String toString() {
    return id;
  }
}
