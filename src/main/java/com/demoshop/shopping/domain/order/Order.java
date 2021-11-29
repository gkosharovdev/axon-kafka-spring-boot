package com.demoshop.shopping.domain.order;

import com.demoshop.shopping.application.order.commands.AbandonOrder;
import com.demoshop.shopping.application.order.commands.AddItemsOfProduct;
import com.demoshop.shopping.application.order.commands.DropItemsOfProduct;
import com.demoshop.shopping.application.order.commands.InitiateOrder;
import com.demoshop.shopping.domain.order.events.ItemsOfProductAdded;
import com.demoshop.shopping.domain.order.events.ItemsOfProductRemoved;
import com.demoshop.shopping.domain.order.events.OrderAbandoned;
import com.demoshop.shopping.domain.order.events.OrderInitiated;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class Order {

  @AggregateIdentifier private OrderId orderId;

  private Map<String, ItemsOfProduct> items;
  private CustomerId customerId;

  public Order() {}

  @CommandHandler
  public Order(InitiateOrder command) {
    var event = OrderInitiated.of(command.getOrderId(), command.getCustomerId());
    AggregateLifecycle.apply(event);
    log.info("Customer {} initiated an order {}", command.getCustomerId(), command.getOrderId());
  }

  @CommandHandler
  public void handleCommand(AddItemsOfProduct command) {
    var event =
        ItemsOfProductAdded.of(
            command.getProductId(),
            command.getOrderId(),
            command.getNumberOfItems(),
            command.getCostPerItem());
    AggregateLifecycle.apply(event);
    log.info(
        "{} items of {} added to order {}",
        command.getNumberOfItems(),
        command.getProductId(),
        command.getOrderId());
  }

  @CommandHandler
  public void handleCommand(DropItemsOfProduct command) {
    var event =
        ItemsOfProductRemoved.of(
            command.getOrderId(), command.getProductId(), command.getNumberOfItems());
    AggregateLifecycle.apply(event);
    log.info(
        "{} items of {} removed from order {}",
        command.getNumberOfItems(),
        command.getProductId(),
        command.getOrderId());
  }

  @CommandHandler
  public void handleCommand(AbandonOrder command) {
    var event = OrderAbandoned.of(command.getOrderId());
    AggregateLifecycle.apply(event);
    log.info("Order {} abandoned", command.getOrderId());
  }

  @EventSourcingHandler
  public void applyEvent(OrderInitiated event) {
    this.orderId = event.getOrderId();
    this.customerId = event.getCustomerId();
  }

  @EventSourcingHandler
  public void applyEvent(ItemsOfProductAdded event) {
    this.orderId = event.getOrderId();
    Optional.ofNullable(this.items)
        .ifPresentOrElse(
            items ->
                items.put(
                    event.getProductId(),
                    ItemsOfProduct.of(
                        event.getProductId(), event.getQuantity(), event.getPricePerItem())),
            () -> {
              this.items = new HashMap<>();
              if (this.items.containsKey(event.getProductId())) {
                var itemsOfProduct = items.get(event.getProductId());
                int quantity = itemsOfProduct.getQuantity() + event.getQuantity();
                this.items.put(
                    event.getProductId(),
                    ItemsOfProduct.of(event.getProductId(), quantity, event.getPricePerItem()));
              } else {
                this.items.put(
                    event.getProductId(),
                    ItemsOfProduct.of(
                        event.getProductId(), event.getQuantity(), event.getPricePerItem()));
              }
            });
  }

  @EventSourcingHandler
  public void applyEvent(ItemsOfProductRemoved event) {
    assertItemsOfProductExist(event.getProductId());
    assertEnoughQuantityOfItems(event.getProductId(), event.getQuantity());

    var itemsOfProduct = this.items.get(event.getProductId());
    if (itemsOfProduct.getQuantity() > event.getQuantity()) {
      int newQuantity = itemsOfProduct.getQuantity() - event.getQuantity();
      this.items.put(
          event.getProductId(),
          ItemsOfProduct.of(event.getProductId(), newQuantity, itemsOfProduct.getCost()));
    } else {
      this.items.remove(event.getProductId());
    }
  }

  @EventSourcingHandler
  public void applyEvent(OrderAbandoned event) {
    AggregateLifecycle.markDeleted();
  }

  private void assertEnoughQuantityOfItems(String productId, int quantity) {
    if (this.items.containsKey(productId) && this.items.get(productId).getQuantity() < quantity) {
      throw new NotAsManyItemsException(this.orderId, productId);
    }
  }

  private void assertItemsOfProductExist(String productId) {
    if (!this.items.containsKey(productId)) {
      throw new NoSuchItemsException(this.orderId, productId);
    }
  }
}
