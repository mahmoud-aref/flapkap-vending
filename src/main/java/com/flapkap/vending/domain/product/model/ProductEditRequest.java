package com.flapkap.vending.domain.product.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductEditRequest {
  private String name;
  private int price;
  private String description;
}
