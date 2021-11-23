package com.demoshop.shopping.domain.product;

import lombok.Data;
import org.javamoney.moneta.Money;
import org.springframework.data.annotation.Version;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String productId;
    private Money pricePerItem;
    @Version
    private Long version;

}
