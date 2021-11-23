package com.demoshop.shopping.application.order;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderApplicationServiceImpl implements OrderApplicationService {


    private final CommandGateway commandGateway;

    @Override
    public void abandonOrder(String orderId) {

    }

    @Override
    public void checkoutOrder(String orderId) {

    }

    @Override
    public void deanonymizeOrder(String customerId) {

    }

    @Override
    public void anonymizeOrder(String orderId) {

    }

    @Override
    public void addItemsOfProduct(String productId, int quantity) {

    }

    @Override
    public void dropItems(String productId, int quantity) {

    }
}
