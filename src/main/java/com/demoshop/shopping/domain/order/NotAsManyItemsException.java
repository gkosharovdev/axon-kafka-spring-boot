package com.demoshop.shopping.domain.order;

public class NotAsManyItemsException extends RuntimeException {
    public NotAsManyItemsException(OrderId orderId, ProductId productId) {
        super(String.format("Order %s does not contain as many items of %s", orderId.toString(), productId.toString()));
    }
}
