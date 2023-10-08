package com.flapkap.vending.domain.machine.model;

import lombok.Data;

@Data
public class FillMachineRequest {
  private int amount;
  private int productId;
}
