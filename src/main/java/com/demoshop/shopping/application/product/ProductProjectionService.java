package com.demoshop.shopping.application.product;

import com.demoshop.shopping.infrastructure.messaging.inventory.ProductSupplied;

public interface ProductProjectionService {
  void applyEvent(ProductSupplied event);
}
