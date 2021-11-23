package com.demoshop.shopping.application.order;

import com.demoshop.shopping.application.order.commands.AbandonOrder;
import com.demoshop.shopping.application.order.commands.AddItemsOfProduct;
import com.demoshop.shopping.application.order.commands.CheckoutOrder;
import com.demoshop.shopping.application.order.commands.DropItemsOfProduct;
import com.demoshop.shopping.application.order.commands.InitiateOrder;
import com.demoshop.shopping.domain.order.CustomerId;
import com.demoshop.shopping.domain.order.OrderId;
import com.demoshop.shopping.domain.product.ProductRepository;
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
  public java.lang.String initiateOrder(java.lang.String customerId) {
    var orderId = OrderId.from(CustomerId.of(customerId));
    var initiateOrderCommand = InitiateOrder.of(orderId, CustomerId.of(customerId));
    commandGateway.sendAndWait(initiateOrderCommand);
    return orderId.toString();
  }

  @Override
  public void abandonOrder(java.lang.String orderId) {
    commandGateway.send(AbandonOrder.of(OrderId.of(orderId)));
  }

  @Override
  public void checkoutOrder(java.lang.String orderId) {
    commandGateway.send(CheckoutOrder.of(OrderId.of(orderId)));
  }

  @Override
  public void addItemsOfProduct(java.lang.String orderId, java.lang.String productId, int quantity) {
    productRepository
        .findById(productId)
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
  public void dropItems(java.lang.String orderId, java.lang.String productId, int quantity) {
    var command = DropItemsOfProduct.of(OrderId.of(orderId), productId, quantity);
    commandGateway.send(command);
  }
}
