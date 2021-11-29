package com.demoshop.shopping.domain.order;

import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class ItemsOfProduct {
    String productId;
    int quantity;
    BigDecimal cost;
}
