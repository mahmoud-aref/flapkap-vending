package com.flapkap.vending.domain.slot.service.impl;

import com.flapkap.vending.domain.commons.BasicResponse;
import com.flapkap.vending.domain.slot.model.SlotCreationRequest;
import com.flapkap.vending.domain.slot.model.SlotDTO;
import com.flapkap.vending.domain.slot.model.SlotMapper;
import com.flapkap.vending.domain.slot.service.SlotService;
import com.flapkap.vending.infrastructure.slot.datasource.jpa.SlotRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SlotServiceImpl implements SlotService {

  private final SlotRepository slotRepository;
  private final SlotMapper slotMapper;

  @Override
  public BasicResponse addNewSlot(SlotCreationRequest request) {
    var slotEntity = slotMapper.toEntity(request);
    slotRepository.save(slotEntity);
    return BasicResponse.builder().message("slot added").build();
  }

  // return slots of all machines
  @Override
  public List<SlotDTO> listAllSlots() {
    return slotRepository.findAll().stream().map(slotMapper::toDTO).toList();
  }

  @Override
  public BasicResponse deleteSlot(int slotId) {
    slotRepository.deleteById(slotId);
    return BasicResponse.builder().message("slot with id " + slotId + " is deleted").build();
  }
}
