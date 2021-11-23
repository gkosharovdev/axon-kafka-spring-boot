package com.demoshop.shopping.infrastructure.messaging.inventory;

import lombok.Value;

@Value(staticConstructor = "of")
public class ProductSupplied {
    String productId;
}
