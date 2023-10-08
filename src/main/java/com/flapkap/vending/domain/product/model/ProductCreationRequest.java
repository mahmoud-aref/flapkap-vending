package com.flapkap.vending.domain.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreationRequest {
  private String name;
  private String description;
  private int price;
  private int quantity;
}
