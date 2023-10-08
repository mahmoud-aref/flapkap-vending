package com.flapkap.vending.infrastructure.product.datasource.jpa;

import com.flapkap.vending.domain.product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {}
