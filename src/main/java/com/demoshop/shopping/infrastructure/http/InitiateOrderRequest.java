package com.demoshop.shopping.infrastructure.http;

import lombok.Value;

@Value(staticConstructor = "of")
public class InitiateOrderRequest {
    String customerId;
}
