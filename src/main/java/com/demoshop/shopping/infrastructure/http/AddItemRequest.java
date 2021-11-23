package com.demoshop.shopping.infrastructure.http;

import lombok.Value;

@Value
public class AddItemRequest {
    String orderId;
    String itemId;
}
