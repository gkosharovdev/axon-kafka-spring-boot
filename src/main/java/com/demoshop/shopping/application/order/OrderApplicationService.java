package com.demoshop.shopping.application.order;

public interface OrderApplicationService {

  String initiateOrder(String customerId);

  void abandonOrder(String orderId);

  void checkoutOrder(String orderId);

  void addItemsOfProduct(String orderId, String productId, int quantity);

  void dropItems(String orderId, String itemId, int quantity);
}
