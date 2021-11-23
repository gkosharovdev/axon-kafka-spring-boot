package com.demoshop.shopping.domain.order.events;

import com.demoshop.shopping.domain.order.OrderId;
import com.demoshop.shopping.domain.order.ProductId;
import lombok.Value;

@Value(staticConstructor = "of")
public class ItemsOfProductRemoved {
    OrderId orderId;
    ProductId productId;
    int quantity;
}
