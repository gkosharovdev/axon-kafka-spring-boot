package com.demoshop.shopping.application.order.commands;

import com.demoshop.shopping.domain.order.ProductId;
import com.demoshop.shopping.domain.order.OrderId;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@Value(staticConstructor = "of")
public class AddItemsOfProduct {
  @TargetAggregateIdentifier OrderId orderId;
  ProductId productId;
  int numberOfItems;
  MonetaryAmount costPerItem;
  ZonedDateTime addedAt;
}
