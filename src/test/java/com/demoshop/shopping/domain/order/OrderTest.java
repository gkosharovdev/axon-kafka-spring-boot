package com.demoshop.shopping.domain.order;

import com.demoshop.shopping.application.order.commands.AbandonOrder;
import com.demoshop.shopping.application.order.commands.AddItemsOfProduct;
import com.demoshop.shopping.application.order.commands.DropItemsOfProduct;
import com.demoshop.shopping.application.order.commands.InitiateOrder;
import com.demoshop.shopping.domain.order.events.ItemsOfProductAdded;
import com.demoshop.shopping.domain.order.events.ItemsOfProductRemoved;
import com.demoshop.shopping.domain.order.events.OrderAbandoned;
import com.demoshop.shopping.domain.order.events.OrderInitiated;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderTest {

  private FixtureConfiguration<Order> fixture;

  @BeforeEach
  public void setup() {
    fixture = new AggregateTestFixture<>(Order.class);
  }

  @Test
  void should_be_able_to_initiate_order() {
    var testCustomer = CustomerId.of("TEST_CUSTOMER");
    var testOrder = OrderId.from(testCustomer);
    var command = InitiateOrder.of(testOrder, testCustomer);
    fixture
        .given()
        .when(command)
        .expectSuccessfulHandlerExecution()
        .expectEvents(OrderInitiated.of(testOrder, testCustomer));
  }

  @Test
  void should_be_able_to_add_items_of_product() {
    var testCustomer = CustomerId.of("TEST_CUSTOMER");
    var testOrder = OrderId.of("TEST_ORDER_1");
    var testProduct = "TEST_PRODUCT";
    var addItemsOfProduct = AddItemsOfProduct.of(testOrder, testProduct, 5, Money.of(5, "EUR"));
    fixture
        .given(OrderInitiated.of(testOrder, testCustomer))
        .when(addItemsOfProduct)
        .expectSuccessfulHandlerExecution()
        .expectEvents(ItemsOfProductAdded.of(testProduct, testOrder, 5, Money.of(5, "EUR")));
  }

  @Test
  void given_some_items_of_product_already_added_should_be_able_to_add_quantity() {
    var testCustomer = CustomerId.of("TEST_CUSTOMER");
    var testOrder = OrderId.of("TEST_ORDER_1");
    var testProduct = "TEST_PRODUCT";
    var addItemsOfProduct = AddItemsOfProduct.of(testOrder, testProduct, 5, Money.of(5, "EUR"));
    fixture
        .given(OrderInitiated.of(testOrder, testCustomer), ItemsOfProductAdded.of(testProduct, testOrder, 3, Money.of(5, "EUR")))
        .when(addItemsOfProduct)
        .expectSuccessfulHandlerExecution()
        .expectEvents(ItemsOfProductAdded.of(testProduct, testOrder, 5, Money.of(5, "EUR")));
  }

  @Test
  void given_some_items_of_product_already_added_should_be_able_to_remove_items() {
    var testCustomer = CustomerId.of("TEST_CUSTOMER");
    var testOrder = OrderId.of("TEST_ORDER_1");
    var testProduct = "TEST_PRODUCT";
    var removeItemsOfProduct = DropItemsOfProduct.of(testOrder, testProduct, 1);
    fixture
        .given(OrderInitiated.of(testOrder, testCustomer), ItemsOfProductAdded.of(testProduct, testOrder, 3, Money.of(5, "EUR")))
        .when(removeItemsOfProduct)
        .expectSuccessfulHandlerExecution()
        .expectEvents(ItemsOfProductRemoved.of(testOrder, testProduct, 1));
  }

  @Test
  void should_be_able_to_abandon_order() {
    var testCustomer = CustomerId.of("TEST_CUSTOMER");
    var testOrder = OrderId.of("TEST_ORDER_1");
    var testProduct = "TEST_PRODUCT";
    var abandonOrder = AbandonOrder.of(testOrder);
    fixture
        .given(OrderInitiated.of(testOrder, testCustomer), ItemsOfProductAdded.of(testProduct, testOrder, 3, Money.of(5, "EUR")))
        .when(abandonOrder)
        .expectSuccessfulHandlerExecution()
        .expectEvents(OrderAbandoned.of(testOrder));
  }
}
