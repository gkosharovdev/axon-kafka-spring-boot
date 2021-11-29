package com.demoshop.shopping.application.order.commands;

import com.demoshop.shopping.domain.order.OrderId;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class AddItemsOfProduct {
  @TargetAggregateIdentifier OrderId orderId;
  String productId;
  int numberOfItems;
  BigDecimal costPerItem;
}
