package com.demoshop.shopping.application.order;

import com.demoshop.shopping.application.order.commands.AbandonOrder;
import com.demoshop.shopping.application.order.commands.AddItemsOfProduct;
import com.demoshop.shopping.application.order.commands.CheckoutOrder;
import com.demoshop.shopping.application.order.commands.DropItemsOfProduct;
import com.demoshop.shopping.application.order.commands.InitiateOrder;
import com.demoshop.shopping.domain.order.OrderId;
import com.demoshop.shopping.domain.product.Product;
import com.demoshop.shopping.domain.product.ProductRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderApplicationServiceTest {

    private static final String CUSTOMER_ID = "TEST_CUSTOMER";
    private static final String PRODUCT_ID = "TEST_PRODUCT";
    private static final Product TEST_PRODUCT = new Product(PRODUCT_ID, new BigDecimal(5));
    private static final String ORDER_ID = "ORDER_1_BY_TEST_CUSTOMER";

    @InjectMocks
    private OrderApplicationServiceImpl orderApplicationService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CommandGateway commandGateway;

    @Test
    void should_dispatch_initiate_order_command() {
        orderApplicationService.initiateOrder(CUSTOMER_ID);
        ArgumentCaptor<InitiateOrder> argument = ArgumentCaptor.forClass(InitiateOrder.class);
        verify(commandGateway).sendAndWait(argument.capture());
    }

    @Test
    void should_dispatch_add_items_of_product_command() {
        when(productRepository.findByProductId(anyString())).thenReturn(Optional.of(TEST_PRODUCT));
        orderApplicationService.addItemsOfProduct(ORDER_ID, PRODUCT_ID, 5);
        ArgumentCaptor<AddItemsOfProduct> argument = ArgumentCaptor.forClass(AddItemsOfProduct.class);
        verify(commandGateway).send(argument.capture());
        assertThat(argument.getValue().getOrderId()).isEqualTo(OrderId.of(ORDER_ID));
        assertThat(argument.getValue().getProductId()).isEqualTo(PRODUCT_ID);
    }

    @Test
    void given_product_not_present_should_not_dispatch_command() {
        when(productRepository.findByProductId(anyString())).thenReturn(Optional.empty());
        orderApplicationService.addItemsOfProduct(ORDER_ID, PRODUCT_ID, 5);
        ArgumentCaptor<AddItemsOfProduct> argument = ArgumentCaptor.forClass(AddItemsOfProduct.class);
        verify(commandGateway, never()).send(argument.capture());
    }

    @Test
    void should_dispatch_drop_items_of_product_command() {
        orderApplicationService.dropItems(ORDER_ID, PRODUCT_ID, 5);
        ArgumentCaptor<DropItemsOfProduct> argument = ArgumentCaptor.forClass(DropItemsOfProduct.class);
        verify(commandGateway).send(argument.capture());
        assertThat(argument.getValue().getOrderId()).isEqualTo(OrderId.of(ORDER_ID));
        assertThat(argument.getValue().getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(argument.getValue().getNumberOfItems()).isEqualTo(5);
    }

    @Test
    void should_dispatch_abandon_order_command() {
        orderApplicationService.abandonOrder(ORDER_ID);
        ArgumentCaptor<AbandonOrder> argument = ArgumentCaptor.forClass(AbandonOrder.class);
        verify(commandGateway).send(argument.capture());
        assertThat(argument.getValue().getOrderId()).isEqualTo(OrderId.of(ORDER_ID));
    }

    @Test
    void should_dispatch_checkout_order_command() {
        orderApplicationService.checkoutOrder(ORDER_ID);
        ArgumentCaptor<CheckoutOrder> argument = ArgumentCaptor.forClass(CheckoutOrder.class);
        verify(commandGateway).send(argument.capture());
        assertThat(argument.getValue().getOrderId()).isEqualTo(OrderId.of(ORDER_ID));
    }
}