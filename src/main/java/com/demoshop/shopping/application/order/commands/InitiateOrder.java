package com.demoshop.shopping.application.order.commands;

import com.demoshop.shopping.domain.order.CustomerId;
import com.demoshop.shopping.domain.order.OrderId;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value(staticConstructor = "of")
public class InitiateOrder {
  @TargetAggregateIdentifier OrderId orderId;
  CustomerId customerId;
}
