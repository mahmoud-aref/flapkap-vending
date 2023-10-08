package com.flapkap.vending.domain.slot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlotCreationRequest {
  private String name;
  private String machineId;
  private String capacity;
}
