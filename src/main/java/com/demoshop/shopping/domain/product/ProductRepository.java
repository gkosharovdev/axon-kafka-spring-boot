package com.demoshop.shopping.domain.product;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

  Optional<Product> findByProductId(String productId);

  void deleteByProductId(String productId);
}
