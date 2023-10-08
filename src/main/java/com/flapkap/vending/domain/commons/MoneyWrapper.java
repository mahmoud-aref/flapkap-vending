package com.flapkap.vending.domain.commons;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoneyWrapper {
  private CashType type;
  private int amount;
}
