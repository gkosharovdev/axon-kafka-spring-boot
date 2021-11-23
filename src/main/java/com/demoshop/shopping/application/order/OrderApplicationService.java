package com.demoshop.shopping.application.order;

public interface OrderApplicationService {

  void abandonOrder(String orderId);

  void checkoutOrder(String orderId);

  void deanonymizeOrder(String customerId);

  void anonymizeOrder(String orderId);

  void addItemsOfProduct(String productId, int quantity);

  void dropItems(String itemId, int quantity);
}
