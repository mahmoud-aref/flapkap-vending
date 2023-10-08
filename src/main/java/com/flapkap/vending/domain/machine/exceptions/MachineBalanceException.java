package com.flapkap.vending.domain.machine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MachineBalanceException extends RuntimeException {
  public MachineBalanceException(String message) {
    super(message);
  }
}
