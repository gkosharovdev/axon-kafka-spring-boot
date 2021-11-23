package com.demoshop.shopping.infrastructure.messaging.inventory;

import com.demoshop.shopping.application.product.ProductProjectionService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.MessageBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductEventListenerTest {

    @Autowired
    private InputDestination inputDestination;

    @MockBean
    private ProductProjectionService productService;

    @SneakyThrows
    @Test
    void should_injest_product_events() {
        inputDestination.send(MessageBuilder.withPayload(ProductSupplied.of("Apples"))
                .setHeader("to_process", true)
                .build());
        verify(productService).applyEvent(any());
    }

    @SpringBootApplication
    @Import({TestChannelBinderConfiguration.class})
    class SampleConfiguration {
    }
}