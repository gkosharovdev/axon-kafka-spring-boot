package com.demoshop.shopping.infrastructure.http;

import lombok.Value;

@Value(staticConstructor = "of")
public class AddItemRequest {
  String orderId;
  String itemId;
  int quantity;
}
