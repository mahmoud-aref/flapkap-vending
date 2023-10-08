package com.flapkap.vending.domain.machine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineCreationRequest {
  private String name;
  private String address;
  private int capacity;
}
