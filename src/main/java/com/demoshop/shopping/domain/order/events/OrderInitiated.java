package com.demoshop.shopping.domain.order.events;

import com.demoshop.shopping.domain.order.CustomerId;
import com.demoshop.shopping.domain.order.OrderId;
import lombok.Value;

@Value(staticConstructor = "of")
public class OrderInitiated {
  OrderId orderId;
  CustomerId customerId;
}
