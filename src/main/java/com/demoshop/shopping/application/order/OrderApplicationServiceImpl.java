package com.demoshop.shopping.application.order;

import com.demoshop.shopping.application.order.commands.AbandonOrder;
import com.demoshop.shopping.application.order.commands.AddItemsOfProduct;
import com.demoshop.shopping.application.order.commands.CheckoutOrder;
import com.demoshop.shopping.application.order.commands.DropItemsOfProduct;
import com.demoshop.shopping.application.order.commands.InitiateOrder;
import com.demoshop.shopping.domain.order.CustomerId;
import com.demoshop.shopping.domain.order.OrderId;
import com.demoshop.shopping.domain.product.ProductRepository;
import com.demoshop.shopping.infrastructure.http.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderApplicationServiceImpl implements OrderApplicationService {

  private final CommandGateway commandGateway;
  private final ProductRepository productRepository;

  @Override
  public String initiateOrder(String customerId) {
    var orderId = OrderId.from(CustomerId.of(customerId));
    var initiateOrderCommand = InitiateOrder.of(orderId, CustomerId.of(customerId));
    commandGateway.sendAndWait(initiateOrderCommand);
    return orderId.toString();
  }

  @Override
  public void abandonOrder(String orderId) {
    commandGateway.send(AbandonOrder.of(OrderId.of(orderId)));
  }

  @Override
  public void checkoutOrder(String orderId) {
    commandGateway.send(CheckoutOrder.of(OrderId.of(orderId)));
  }

  @Override
  public void addItemsOfProduct(String orderId, String productId, int quantity) {
    productRepository
        .findByProductId(productId)
        .ifPresentOrElse(
            product -> {
              var command =
                  AddItemsOfProduct.of(
                      OrderId.of(orderId),
                      product.getProductId(),
                      quantity,
                      product.getPricePerItem());
              commandGateway.send(command);
            },
            () -> log.info("No product {} found while shopping", productId));
  }

  @Override
  public void dropItems(String orderId, String productId, int quantity) {
    var command = DropItemsOfProduct.of(OrderId.of(orderId), productId, quantity);
    commandGateway.send(command);
  }
}
