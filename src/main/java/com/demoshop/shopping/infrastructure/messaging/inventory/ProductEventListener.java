package com.demoshop.shopping.infrastructure.messaging.inventory;

import com.demoshop.shopping.application.product.ProductProjectionService;
import io.cloudevents.CloudEvent;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductEventListener {

  @Autowired private ProductProjectionService productProjectionService;

  @Bean
  public Consumer<CloudEvent> onProductEvent() {
    return event -> {
      productProjectionService.applyEvent(ProductSupplied.of("SomeProduct"));
      log.info("Processing event: {}", event);
    };
  }
}
