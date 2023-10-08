package com.flapkap.vending.domain.slot.model;

import com.flapkap.vending.domain.machine.model.MachineDTO;
import com.flapkap.vending.domain.product.model.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotDTO {
  private int id;
  private String name;
  private MachineDTO machine;
  private ProductDTO product;
  private int capacity;
  private int quantity;
}
