package com.demoshop.shopping.infrastructure.http;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShoppingController {

    @PostMapping("/v1/orders/{orderId}")
    void addItem(@RequestBody AddItemRequest addItemRequest) {

    }
}
