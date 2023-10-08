package com.flapkap.vending.domain.machine.model;

import com.flapkap.vending.domain.commons.MoneyWrapper;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PurchaseResponse {
  private boolean success;
  private String message;
  private List<MoneyWrapper> change;
}
