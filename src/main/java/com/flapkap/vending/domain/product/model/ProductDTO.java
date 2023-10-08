package com.flapkap.vending.domain.product.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
  private Integer id;
  private String name;
  private String description;
  private BigDecimal price;
  private Integer quantity;
}
