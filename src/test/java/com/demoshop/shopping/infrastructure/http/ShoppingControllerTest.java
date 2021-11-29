package com.demoshop.shopping.infrastructure.http;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.demoshop.shopping.application.order.OrderApplicationService;
import com.demoshop.shopping.application.order.OrderApplicationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(ShoppingController.class)
@Import(OrderApplicationServiceImpl.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ShoppingControllerTest {
  @Autowired private MockMvc mockMvc;

  @MockBean private OrderApplicationService orderApplicationService;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void should_not_get_all_orders_without_constraints() throws Exception {
    this.mockMvc.perform(get("/v1/orders")).andDo(print()).andExpect(status().is4xxClientError());
  }

  @Test
  void should_create_order_for_a_customer() throws Exception {
    when(orderApplicationService.initiateOrder("georgi")).thenReturn("georgi-test-order-1");
    mockMvc
        .perform(
            post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(InitiateOrderRequest.of("georgi"))))
        .andDo(print())
        .andExpect(status().isCreated());
  }

  @Test
  void should_add_an_item_to_order() throws Exception {
    mockMvc
        .perform(
            post("/v1/orders/georgi-test-order-2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(
                        AddItemRequest.of("georgi-test-order-2", "apple", 1))))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
