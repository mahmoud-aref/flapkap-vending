package com.flapkap.vending.domain.machine.service.impl;

import static com.flapkap.vending.domain.commons.CashType.BANKNOTE_100;
import static com.flapkap.vending.domain.commons.CashType.BANKNOTE_20;
import static com.flapkap.vending.domain.commons.CashType.BANKNOTE_50;
import static com.flapkap.vending.domain.commons.CashType.COIN_10;
import static com.flapkap.vending.domain.commons.CashType.COIN_5;

import com.flapkap.vending.domain.commons.BasicResponse;
import com.flapkap.vending.domain.commons.CashType;
import com.flapkap.vending.domain.commons.ItemNotFoundException;
import com.flapkap.vending.domain.commons.MoneyWrapper;
import com.flapkap.vending.domain.commons.UnsupportedTypeException;
import com.flapkap.vending.domain.machine.exceptions.MachineBalanceException;
import com.flapkap.vending.domain.machine.exceptions.MachineGeneralException;
import com.flapkap.vending.domain.machine.model.FillMachineRequest;
import com.flapkap.vending.domain.machine.model.MachineCreationRequest;
import com.flapkap.vending.domain.machine.model.MachineDTO;
import com.flapkap.vending.domain.machine.model.MachineEditRequest;
import com.flapkap.vending.domain.machine.model.MachineEntity;
import com.flapkap.vending.domain.machine.model.MachineMapper;
import com.flapkap.vending.domain.machine.model.PurchaseRequest;
import com.flapkap.vending.domain.machine.model.PurchaseResponse;
import com.flapkap.vending.domain.machine.service.MachineService;
import com.flapkap.vending.domain.slot.model.SlotEntity;
import com.flapkap.vending.infrastructure.machine.datasource.jpa.MachineRepository;
import com.flapkap.vending.infrastructure.product.datasource.jpa.ProductRepository;
import com.flapkap.vending.infrastructure.slot.datasource.jpa.SlotRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MachineServiceImpl implements MachineService {

  private final MachineRepository machineRepository;
  private final ProductRepository productRepository;
  private final SlotRepository slotRepository;

  private final MachineMapper machineMapper;

  public static final int SLOT_MAX_CAPACITY = 10;

  @Override
  public MachineDTO listMachine(int machineId) {
    return machineRepository
        .findById(machineId)
        .map(machineMapper::toDTO)
        .orElseThrow(() -> new RuntimeException("machine with id " + machineId + " not found"));
  }

  @Override
  public BasicResponse addNewMachine(MachineCreationRequest request) {
    var machineEntity = machineMapper.toEntity(request);
    machineRepository.save(machineEntity);
    return BasicResponse.builder().message("machine added").build();
  }

  // no pagination needed for now
  @Override
  public List<MachineDTO> listAllMachines() {
    return machineRepository.findAll().stream().map(machineMapper::toDTO).toList();
  }

  @Override
  public BasicResponse editMachine(int machineId, MachineEditRequest request) {
    var machine = getById(machineId);
    machine.setAddress(request.getAddress());
    machine.setName(request.getName());
    machineRepository.save(machine);
    return BasicResponse.builder().message("machine with id " + machineId + " is edited").build();
  }

  @Override
  public BasicResponse deleteMachine(int machineId) {
    machineRepository.deleteById(machineId);
    return BasicResponse.builder().message("machine with id " + machineId + " is deleted").build();
  }

  @Override
  public BasicResponse depositMoney(int machineId, MoneyWrapper request) {

    var machine = getById(machineId);

    switch (request.getType()) {
      case COIN_5 -> {
        machine.setFives(machine.getFives() + request.getAmount());
        increaseBalance(machine, 5, request.getAmount());
      }
      case COIN_10 -> {
        machine.setTens(machine.getTens() + request.getAmount());
        increaseBalance(machine, 10, request.getAmount());
      }
      case BANKNOTE_20 -> {
        machine.setTwenties(machine.getTwenties() + request.getAmount());
        increaseBalance(machine, 20, request.getAmount());
      }
      case BANKNOTE_50 -> {
        machine.setFifties(machine.getFifties() + request.getAmount());
        increaseBalance(machine, 50, request.getAmount());
      }
      case BANKNOTE_100 -> {
        machine.setHundreds(machine.getHundreds() + request.getAmount());
        increaseBalance(machine, 100, request.getAmount());
      }
    }

    machineRepository.save(machine);
    return BasicResponse.builder().message("money deposited").build();
  }

  @Override
  public BasicResponse withdrawAllMoney(int machineId) {
    var machine = getById(machineId);
    if (machine.getBalance() == 0) {
      throw new MachineBalanceException("machine balance is zero");
    }
    machine.setFives(0);
    machine.setTens(0);
    machine.setTwenties(0);
    machine.setFifties(0);
    machine.setHundreds(0);
    machine.setBalance(0);
    return BasicResponse.builder().message("money withdrawn check money drawer").build();
  }

  @Override
  public BasicResponse withdrawMoney(int machineId, MoneyWrapper request) {
    var machine = getById(machineId);

    switch (request.getType()) {
      case COIN_5 -> {
        if (machine.getFives() < request.getAmount()) {
          throw new MachineBalanceException("not enough fives");
        }
        machine.setFives(machine.getFives() - request.getAmount());
        decreaseBalance(machine, 5, request.getAmount());
      }
      case COIN_10 -> {
        if (machine.getTens() < request.getAmount()) {
          throw new MachineBalanceException("not enough tens");
        }
        machine.setTens(machine.getTens() - request.getAmount());
        decreaseBalance(machine, 10, request.getAmount());
      }
      case BANKNOTE_20 -> {
        if (machine.getTwenties() < request.getAmount()) {
          throw new MachineBalanceException("not enough twenties");
        }
        machine.setTwenties(machine.getTwenties() - request.getAmount());
        decreaseBalance(machine, 20, request.getAmount());
      }
      case BANKNOTE_50 -> {
        if (machine.getFifties() < request.getAmount()) {
          throw new MachineBalanceException("not enough fifties");
        }
        machine.setFifties(machine.getFifties() - request.getAmount());
        decreaseBalance(machine, 50, request.getAmount());
      }
      case BANKNOTE_100 -> {
        if (machine.getHundreds() < request.getAmount()) {
          throw new MachineBalanceException("not enough hundreds");
        }
        machine.setHundreds(machine.getHundreds() - request.getAmount());
        decreaseBalance(machine, 100, request.getAmount());
      }
      default -> throw new RuntimeException("invalid money type");
    }

    machineRepository.save(machine);
    return BasicResponse.builder().message("money withdrawn check money drawer").build();
  }

  @Override
  public BasicResponse addProduct(int machineId, FillMachineRequest request) {

    var machine = getById(machineId);
    var product =
        productRepository
            .findById(request.getProductId())
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        "product with id " + request.getProductId() + " not found"));

    var slotProduct =
        machine.getSlots().stream()
            .filter(
                slot ->
                    (slot.getProduct().getId() == request.getProductId()
                        && slot.getCapacity() > slot.getQuantity() + request.getAmount()));
    slotProduct
        .findFirst()
        .ifPresentOrElse(
            slot -> {
              slot.setQuantity(slot.getQuantity() + 1);
              machineRepository.save(machine);
            },
            () -> {
              if (machine.getMaxSlots() > machine.getSlots().size() + 1)
                throw new MachineGeneralException("machine is full");

              var newSlot =
                  SlotEntity.builder()
                      .product(product)
                      .quantity(request.getAmount())
                      .capacity(SLOT_MAX_CAPACITY)
                      .build();
              machine.getSlots().add(newSlot);
              slotRepository.save(newSlot);
              machineRepository.save(machine);
            });

    return BasicResponse.builder().message("product added").build();
  }

  @Override
  public PurchaseResponse buyProduct(int machineId, PurchaseRequest request) {

    var machine = getById(machineId);

    var scannedCash = getCashValue(request.getType());

    var slot =
        machine.getSlots().stream()
            .filter(s -> s.getProduct().getId() == request.getProductId())
            .findFirst()
            .orElseThrow(
                () ->
                    new ItemNotFoundException(
                        "product with id " + request.getProductId() + " not found"));

    var productsTotalPrice = slot.getProduct().getPrice() * request.getAmount();

    if (slot.getQuantity() < request.getAmount()) {
      throw new MachineGeneralException("not enough product for your request");
    }

    if (scannedCash < productsTotalPrice) {
      throw new MachineGeneralException("not enough money to purchase those products");
    }

    int change = scannedCash - productsTotalPrice;
    if (machine.getBalance() > change) {
      throw new MachineGeneralException("not enough change in machine");
    }

    slot.setQuantity(slot.getQuantity() - request.getAmount());
    slotRepository.save(slot);

    increaseBalance(machine, 1, scannedCash);
    decreaseBalance(machine, 1, change);
    machineRepository.save(machine);

    var product = slot.getProduct();
    product.setQuantity(product.getQuantity() - request.getAmount());
    productRepository.save(product);

    return PurchaseResponse.builder()
        .change(returnChange(machine, change))
        .success(true)
        .message("products purchased successfully please check the tray")
        .build();
  }

  private List<MoneyWrapper> returnChange(MachineEntity machine, int changeAmount) {
    List<MoneyWrapper> moneyChange = new ArrayList<>();
    int[] coins = {5, 10, 20, 50, 100};

    for (int coin : coins) {
      if (changeAmount >= coin) {
        int neededCoins = changeAmount / coin;
        int availableCoins = getMinCoins(machine, neededCoins, getCashType(coin));
        moneyChange.add(
            MoneyWrapper.builder().type(getCashType(coin)).amount(availableCoins).build());
        changeAmount -= availableCoins * coin;
      }
    }

    if (changeAmount != 0) throw new MachineGeneralException("cannot process changes");

    return moneyChange;
  }

  private int getMinCoins(MachineEntity machine, int neededCoins, CashType cashType) {
    return switch (cashType) {
      case COIN_5 -> Math.min(neededCoins, machine.getFives());
      case COIN_10 -> Math.min(neededCoins, machine.getTens());
      case BANKNOTE_20 -> Math.min(neededCoins, machine.getTwenties());
      case BANKNOTE_50 -> Math.min(neededCoins, machine.getFifties());
      case BANKNOTE_100 -> Math.min(neededCoins, machine.getHundreds());
    };
  }

  private int getCashValue(CashType type) {
    return switch (type) {
      case COIN_5 -> 5;
      case COIN_10 -> 10;
      case BANKNOTE_20 -> 20;
      case BANKNOTE_50 -> 50;
      case BANKNOTE_100 -> 100;
    };
  }

  private CashType getCashType(int num) {
    return switch (num) {
      case 5 -> COIN_5;
      case 10 -> COIN_10;
      case 20 -> BANKNOTE_20;
      case 50 -> BANKNOTE_50;
      case 100 -> BANKNOTE_100;
      default -> throw new UnsupportedTypeException("Unexpected value: " + num);
    };
  }

  private void increaseBalance(MachineEntity machine, long multi, long amount) {
    machine.setBalance(machine.getBalance() + ((multi * amount)));
  }

  private void decreaseBalance(MachineEntity machine, long multi, long amount) {
    machine.setBalance(machine.getBalance() - ((multi * amount)));
  }

  private MachineEntity getById(int machineId) {
    return machineRepository
        .findById(machineId)
        .orElseThrow(
            () -> new ItemNotFoundException("machine with id " + machineId + " not found"));
  }
}
