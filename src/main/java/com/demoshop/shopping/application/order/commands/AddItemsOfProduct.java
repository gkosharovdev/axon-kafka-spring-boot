package com.demoshop.shopping.application.order.commands;

import com.demoshop.shopping.domain.order.OrderId;
import java.math.BigDecimal;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value(staticConstructor = "of")
public class AddItemsOfProduct {
  @TargetAggregateIdentifier OrderId orderId;
  String productId;
  int numberOfItems;
  BigDecimal costPerItem;
}
