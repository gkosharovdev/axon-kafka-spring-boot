package com.demoshop.shopping.domain.order;

import lombok.Value;

@Value(staticConstructor = "of")
public class OrderId {
    String id;
}
