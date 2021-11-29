package com.demoshop.shopping.domain.order.events;

import com.demoshop.shopping.domain.order.OrderId;
import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class ItemsOfProductAdded {
    String productId;
    OrderId orderId;
    int quantity;
    BigDecimal pricePerItem;
}
