package com.flapkap.vending.application.machine.controller;

import com.flapkap.vending.domain.commons.BasicResponse;
import com.flapkap.vending.domain.commons.MoneyWrapper;
import com.flapkap.vending.domain.machine.model.FillMachineRequest;
import com.flapkap.vending.domain.machine.model.MachineCreationRequest;
import com.flapkap.vending.domain.machine.model.MachineDTO;
import com.flapkap.vending.domain.machine.model.MachineEditRequest;
import com.flapkap.vending.domain.machine.model.PurchaseRequest;
import com.flapkap.vending.domain.machine.model.PurchaseResponse;
import com.flapkap.vending.domain.machine.service.MachineService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MachineController.MACHINE_PATH)
@RequiredArgsConstructor
public class MachineController {

  public static final String MACHINE_PATH = "/vending/api/v1/machine/";

  public static final String MACHINE_LIST_ONE_PATH = "/{machineId}";
  public static final String MACHINE_ADD_PATH = "/add";
  public static final String MACHINE_EDIT_PATH = "/{machineId}/edit";
  public static final String MACHINE_DELETE_PATH = "/{machineId}/delete";

  public static final String MACHINE_DEPOSIT_PATH = "/{machineId}/deposit";
  public static final String MACHINE_WITHDRAW_PATH = "/{machineId}/withdraw";
  public static final String MACHINE_WITHDRAW_ALL_PATH = "/{machineId}/withdraw/all";

  public static final String MACHINE_ADD_PRODUCT_PATH = "/{machineId}/add-product";
  public static final String MACHINE_BUY_PRODUCT_PATH = "/{machineId}/buy-product";

  private final MachineService machineService;

  @GetMapping
  public ResponseEntity<List<MachineDTO>> listAllMachines() {
    return ResponseEntity.ok(machineService.listAllMachines());
  }

  @GetMapping(MACHINE_LIST_ONE_PATH)
  public ResponseEntity<MachineDTO> listOneMachine(@PathVariable int machineId) {
    return ResponseEntity.ok(machineService.listMachine(machineId));
  }

  @PostMapping(MACHINE_ADD_PATH)
  public ResponseEntity<BasicResponse> addMachine(@RequestBody MachineCreationRequest request) {
    return ResponseEntity.ok(machineService.addNewMachine(request));
  }

  @PutMapping(MACHINE_EDIT_PATH)
  public ResponseEntity<BasicResponse> editMachine(
      @PathVariable int machineId, @RequestBody MachineEditRequest request) {
    return ResponseEntity.ok(machineService.editMachine(machineId, request));
  }

  @PutMapping(MACHINE_DELETE_PATH)
  public ResponseEntity<BasicResponse> deleteMachine(@PathVariable int machineId) {
    return ResponseEntity.ok(machineService.deleteMachine(machineId));
  }

  @PostMapping(MACHINE_DEPOSIT_PATH)
  public ResponseEntity<BasicResponse> depositMoney(
      @PathVariable int machineId, @RequestBody MoneyWrapper request) {
    return ResponseEntity.ok(machineService.depositMoney(machineId, request));
  }

  @PostMapping(MACHINE_WITHDRAW_PATH)
  public ResponseEntity<BasicResponse> withdrawMoney(
      @PathVariable int machineId, @RequestBody MoneyWrapper request) {
    return ResponseEntity.ok(machineService.withdrawMoney(machineId, request));
  }

  @PostMapping(MACHINE_WITHDRAW_ALL_PATH)
  public ResponseEntity<BasicResponse> withdrawAllMoney(@PathVariable int machineId) {
    return ResponseEntity.ok(machineService.withdrawAllMoney(machineId));
  }

  @PostMapping(MACHINE_ADD_PRODUCT_PATH)
  public ResponseEntity<BasicResponse> addProduct(
      @PathVariable int machineId, @RequestBody FillMachineRequest request) {
    return ResponseEntity.ok(machineService.addProduct(machineId, request));
  }

  @PostMapping(MACHINE_BUY_PRODUCT_PATH)
  public ResponseEntity<PurchaseResponse> buyProduct(
      @PathVariable int machineId, @RequestBody PurchaseRequest request) {
    return ResponseEntity.ok(machineService.buyProduct(machineId, request));
  }
}
