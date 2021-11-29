package com.demoshop.shopping.domain.order.events;

import com.demoshop.shopping.domain.order.OrderId;
import java.math.BigDecimal;
import lombok.Value;

@Value(staticConstructor = "of")
public class ItemsOfProductAdded {
  String productId;
  OrderId orderId;
  int quantity;
  BigDecimal pricePerItem;
}
