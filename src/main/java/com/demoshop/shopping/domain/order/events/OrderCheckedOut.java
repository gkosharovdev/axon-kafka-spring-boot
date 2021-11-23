package com.demoshop.shopping.domain.order.events;

import com.demoshop.shopping.domain.order.ItemsOfProduct;
import com.demoshop.shopping.domain.order.OrderId;
import lombok.Value;

import java.util.Set;

@Value(staticConstructor = "of")
public class OrderCheckedOut {
    OrderId orderId;
    Set<ItemsOfProduct> items;
}
