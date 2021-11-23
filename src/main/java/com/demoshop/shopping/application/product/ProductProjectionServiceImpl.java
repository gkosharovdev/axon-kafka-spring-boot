package com.demoshop.shopping.application.product;

import com.demoshop.shopping.infrastructure.messaging.inventory.ProductSupplied;
import org.springframework.stereotype.Service;

@Service
public class ProductProjectionServiceImpl implements ProductProjectionService {
    @Override
    public void applyEvent(ProductSupplied event) {

    }
}

