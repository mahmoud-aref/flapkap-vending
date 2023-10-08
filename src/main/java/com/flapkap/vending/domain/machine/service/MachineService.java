package com.flapkap.vending.domain.machine.service;

import com.flapkap.vending.domain.commons.BasicResponse;
import com.flapkap.vending.domain.commons.MoneyWrapper;
import com.flapkap.vending.domain.machine.model.FillMachineRequest;
import com.flapkap.vending.domain.machine.model.MachineCreationRequest;
import com.flapkap.vending.domain.machine.model.MachineDTO;
import com.flapkap.vending.domain.machine.model.MachineEditRequest;
import com.flapkap.vending.domain.machine.model.PurchaseRequest;
import com.flapkap.vending.domain.machine.model.PurchaseResponse;
import java.util.List;

public interface MachineService {

  MachineDTO listMachine(int machineId);

  BasicResponse addNewMachine(MachineCreationRequest request);

  List<MachineDTO> listAllMachines();

  BasicResponse editMachine(int machineId, MachineEditRequest request);

  BasicResponse deleteMachine(int machineId);

  BasicResponse depositMoney(int machineId, MoneyWrapper request);

  BasicResponse withdrawAllMoney(int machineId);

  BasicResponse withdrawMoney(int machineId, MoneyWrapper request);

  BasicResponse addProduct(int machineId, FillMachineRequest request);

  PurchaseResponse buyProduct(int machineId, PurchaseRequest request);
}
