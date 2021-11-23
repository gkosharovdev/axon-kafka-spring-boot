package com.demoshop.shopping.domain.order;

import lombok.Value;

import javax.money.MonetaryAmount;

@Value(staticConstructor = "of")
public class ItemsOfProduct {
    String productId;
    int quantity;
    MonetaryAmount cost;
}
