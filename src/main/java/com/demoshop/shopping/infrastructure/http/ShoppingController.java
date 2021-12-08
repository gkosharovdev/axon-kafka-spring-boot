package com.demoshop.shopping.infrastructure.http;

import com.demoshop.shopping.application.order.OrderApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/orders")
public class ShoppingController {

  private final OrderApplicationService orderApplicationService;

  @Operation(summary = "Initiate Order for a Customer")
  @PostMapping
  ResponseEntity<?> initiateOrder(@RequestBody InitiateOrderRequest request) {
    var orderId = orderApplicationService.initiateOrder(request.getCustomerId());
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{orderId}")
            .buildAndExpand(orderId)
            .toUri();
    return ResponseEntity.created(uri).build();
  }

  @Operation(summary = "Add Items of a given Product to an Order")
  @PostMapping("/{orderId}")
  void addItems(@PathVariable String orderId, @RequestBody AddItemRequest request) {
    orderApplicationService.addItemsOfProduct(orderId, request.getItemId(), request.getQuantity());
  }
}
