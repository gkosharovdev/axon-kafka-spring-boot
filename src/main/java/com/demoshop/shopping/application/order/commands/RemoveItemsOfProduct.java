package com.demoshop.shopping.application.order.commands;

import com.demoshop.shopping.domain.order.OrderId;
import com.demoshop.shopping.domain.order.ProductId;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value(staticConstructor = "of")
public class RemoveItemsOfProduct {
    @TargetAggregateIdentifier
    OrderId orderId;
    ProductId productId;
    int numberOfItems;
}
