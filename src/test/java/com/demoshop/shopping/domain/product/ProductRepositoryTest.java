package com.demoshop.shopping.domain.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

  @Autowired private ProductRepository productRepository;

  @BeforeEach
  void setUp() {
    var products =
        Arrays.asList(
            buildProduct("Apple", new BigDecimal(1)),
            buildProduct("Table", new BigDecimal(100)),
            buildProduct("Watch", new BigDecimal(250)));
    productRepository.saveAll(products);
  }

  @Test
  void can_persist_and_find_by_product() {
    var product = productRepository.findByProductId("Apple");
    assertThat(product).isPresent();
    assertThat(product)
        .hasValueSatisfying(
            actual -> {
              assertThat(actual.getProductId()).isEqualTo("Apple");
              assertThat(actual.getPricePerItem()).isEqualTo(new BigDecimal(1));
            });
  }

  @Test
  void can_delete() {
    productRepository.deleteByProductId("Apple");
    var product = productRepository.findByProductId("Apple");
    assertThat(product).isNotPresent();
  }

  private Product buildProduct(String productId, BigDecimal price) {
    return new Product(productId, price);
  }
}
