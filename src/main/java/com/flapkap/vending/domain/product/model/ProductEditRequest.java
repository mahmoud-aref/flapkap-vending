package com.flapkap.vending.domain.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEditRequest {
  private String name;
  private int price;
  private String description;
}
