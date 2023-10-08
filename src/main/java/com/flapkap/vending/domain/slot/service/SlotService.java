package com.flapkap.vending.domain.slot.service;

import com.flapkap.vending.domain.commons.BasicResponse;
import com.flapkap.vending.domain.slot.model.SlotCreationRequest;
import com.flapkap.vending.domain.slot.model.SlotDTO;
import java.util.List;

public interface SlotService {
  BasicResponse addNewSlot(SlotCreationRequest request);

  List<SlotDTO> listAllSlots();

  BasicResponse deleteSlot(int slotId);
}
