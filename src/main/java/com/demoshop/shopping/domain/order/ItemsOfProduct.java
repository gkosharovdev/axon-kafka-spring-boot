package com.demoshop.shopping.domain.order;

import java.math.BigDecimal;
import lombok.Value;

@Value(staticConstructor = "of")
public class ItemsOfProduct {
  String productId;
  int quantity;
  BigDecimal cost;
}
