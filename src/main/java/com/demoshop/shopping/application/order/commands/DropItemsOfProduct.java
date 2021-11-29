package com.demoshop.shopping.application.order.commands;

import com.demoshop.shopping.domain.order.OrderId;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value(staticConstructor = "of")
public class DropItemsOfProduct {
  @TargetAggregateIdentifier OrderId orderId;
  String productId;
  int numberOfItems;
}
