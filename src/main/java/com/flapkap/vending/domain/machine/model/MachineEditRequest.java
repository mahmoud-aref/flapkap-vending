package com.flapkap.vending.domain.machine.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MachineEditRequest {
  private String address;
  private String name;
}
