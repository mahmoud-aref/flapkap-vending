package com.flapkap.vending.domain.machine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MachineGeneralException extends RuntimeException {
  public MachineGeneralException(String message) {
    super(message);
  }
}
