package com.demoshop.shopping.domain.order.events;

import com.demoshop.shopping.domain.order.OrderId;
import lombok.Value;

@Value(staticConstructor = "of")
public class ItemsOfProductRemoved {
  OrderId orderId;
  String productId;
  int quantity;
}
