package com.demoshop.shopping.domain.order;

import lombok.Value;

import javax.money.MonetaryAmount;

@Value(staticConstructor = "of")
public class ItemsOfProduct {
    ProductId productId;
    int quantity;
    MonetaryAmount cost;
}
