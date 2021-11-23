package com.demoshop.shopping.domain.order;

public class NoSuchItemsException extends RuntimeException {
    public NoSuchItemsException(OrderId orderId, ProductId productId) {
        super(String.format("Order %s does not contain any items of %s", orderId.toString(), productId.toString()));
    }
}
