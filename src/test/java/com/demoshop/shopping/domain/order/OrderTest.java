package com.demoshop.shopping.domain.order;

import com.demoshop.shopping.application.order.commands.AbandonOrder;
import com.demoshop.shopping.application.order.commands.AddItemsOfProduct;
import com.demoshop.shopping.application.order.commands.AnonymizeOrder;
import com.demoshop.shopping.application.order.commands.DeanonymizeOrder;
import com.demoshop.shopping.application.order.commands.RemoveItemsOfProduct;
import com.demoshop.shopping.domain.order.events.ItemsOfProductAdded;
import com.demoshop.shopping.domain.order.events.ItemsOfProductRemoved;
import com.demoshop.shopping.domain.order.events.OrderAbandoned;
import com.demoshop.shopping.domain.order.events.OrderAnonymized;
import com.demoshop.shopping.domain.order.events.OrderDeanonymized;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderTest {

  private FixtureConfiguration<Order> fixture;

  @BeforeEach
  public void setup() {
    fixture = new AggregateTestFixture<>(Order.class);
  }

  @Test
  void should_be_able_to_add_items_of_product() {
    var testOrder = OrderId.of("TEST_ORDER_1");
    var testProduct = ProductId.of("TEST_PRODUCT");
    var addItemsOfProduct =
        AddItemsOfProduct.of(
            testOrder,
            testProduct,
            5,
            Money.of(5, "EUR"),
            ZonedDateTime.parse("2021-03-10T11:30Z"));
    fixture
        .given()
        .when(addItemsOfProduct)
        .expectSuccessfulHandlerExecution()
        .expectEvents(ItemsOfProductAdded.of(testProduct, testOrder, 5, Money.of(5, "EUR")));
  }

  @Test
  void given_some_items_of_product_already_added_should_be_able_to_add_quantity() {
    var testOrder = OrderId.of("TEST_ORDER_1");
    var testProduct = ProductId.of("TEST_PRODUCT");
    var addItemsOfProduct =
        AddItemsOfProduct.of(
            testOrder,
            testProduct,
            5,
            Money.of(5, "EUR"),
            ZonedDateTime.parse("2021-03-10T11:30Z"));
    fixture
        .given(ItemsOfProductAdded.of(testProduct, testOrder, 3, Money.of(5, "EUR")))
        .when(addItemsOfProduct)
        .expectSuccessfulHandlerExecution()
        .expectEvents(ItemsOfProductAdded.of(testProduct, testOrder, 5, Money.of(5, "EUR")));
  }

  @Test
  void given_some_items_of_product_already_added_should_be_able_to_remove_items() {
    var testOrder = OrderId.of("TEST_ORDER_1");
    var testProduct = ProductId.of("TEST_PRODUCT");
    var removeItemsOfProduct =
        RemoveItemsOfProduct.of(
            testOrder,
            testProduct,
            1);
    fixture
        .given(ItemsOfProductAdded.of(testProduct, testOrder, 3, Money.of(5, "EUR")))
        .when(removeItemsOfProduct)
        .expectSuccessfulHandlerExecution()
        .expectEvents(ItemsOfProductRemoved.of(testOrder, testProduct, 1));
  }

  @Test
  void given_an_anonymous_order_should_be_able_to_deanonymize() {
    var testOrder = OrderId.of("TEST_ORDER_1");
    var testProduct = ProductId.of("TEST_PRODUCT");
    var testCustomer = CustomerId.of("TEST_CUSTOMER");
    var deanonymizeOrder =
            DeanonymizeOrder.of(testOrder, testCustomer);
    fixture
            .given(ItemsOfProductAdded.of(testProduct, testOrder, 3, Money.of(5, "EUR")))
            .when(deanonymizeOrder)
            .expectSuccessfulHandlerExecution()
            .expectEvents(OrderDeanonymized.of(testOrder, testCustomer));
  }

  @Test
  void given_a_not_anonymous_order_should_be_able_to_anonymize() {
    var testOrder = OrderId.of("TEST_ORDER_1");
    var testProduct = ProductId.of("TEST_PRODUCT");
    var testCustomer = CustomerId.of("TEST_CUSTOMER");
    var anonymizeOrder =
            AnonymizeOrder.of(testOrder);
    fixture
            .given(ItemsOfProductAdded.of(testProduct, testOrder, 3, Money.of(5, "EUR")), OrderDeanonymized.of(testOrder, testCustomer))
            .when(anonymizeOrder)
            .expectSuccessfulHandlerExecution()
            .expectEvents(OrderAnonymized.of(testOrder));
  }

  @Test
  void should_be_able_to_abandon_order() {
    var testOrder = OrderId.of("TEST_ORDER_1");
    var testProduct = ProductId.of("TEST_PRODUCT");
    var abandonOrder =
            AbandonOrder.of(testOrder);
    fixture
            .given(ItemsOfProductAdded.of(testProduct, testOrder, 3, Money.of(5, "EUR")))
            .when(abandonOrder)
            .expectSuccessfulHandlerExecution()
            .expectEvents(OrderAbandoned.of(testOrder));

  }
}
