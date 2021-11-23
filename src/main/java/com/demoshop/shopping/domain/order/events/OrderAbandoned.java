package com.demoshop.shopping.domain.order.events;

import com.demoshop.shopping.domain.order.OrderId;
import lombok.Value;

@Value(staticConstructor = "of")
public class OrderAbandoned {
    OrderId orderId;
}
