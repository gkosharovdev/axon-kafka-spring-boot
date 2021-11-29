package com.demoshop.shopping.infrastructure.http;

import java.util.Map;
import lombok.Value;

@Value(staticConstructor = "of")
public class OrderDto {
  String orderId;
  String customerId;
  Map<String, Integer> items;
}
