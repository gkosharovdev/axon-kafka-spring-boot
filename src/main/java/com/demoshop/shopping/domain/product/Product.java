package com.demoshop.shopping.domain.product;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

@Data
@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String productId;
  private BigDecimal pricePerItem;
  @CreatedDate private ZonedDateTime createdAt;
  @LastModifiedDate private ZonedDateTime updatedAt;
  @Version private Long version;

  public Product(String productId, BigDecimal pricePerItem) {
    this.productId = productId;
    this.pricePerItem = pricePerItem;
  }
}
