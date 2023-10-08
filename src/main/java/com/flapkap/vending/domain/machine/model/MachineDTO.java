package com.flapkap.vending.domain.machine.model;

import com.flapkap.vending.domain.slot.model.SlotDTO;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MachineDTO {
  private Integer id;
  private String address;
  private String name;
  private int maxSlots;
  private int balance;
  private List<SlotDTO> slots;
}
