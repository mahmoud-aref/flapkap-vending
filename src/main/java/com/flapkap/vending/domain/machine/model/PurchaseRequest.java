package com.flapkap.vending.domain.machine.model;

import com.flapkap.vending.domain.commons.CashType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseRequest {
  private int productId;
  private int amount;
  private CashType type;
}
