package com.flapkap.vending.application.advice;

import com.flapkap.vending.domain.commons.ErrorResponse;
import com.flapkap.vending.domain.commons.ItemNotFoundException;
import com.flapkap.vending.domain.commons.UnsupportedTypeException;
import com.flapkap.vending.domain.machine.exceptions.MachineBalanceException;
import com.flapkap.vending.domain.machine.exceptions.MachineGeneralException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppCommonControllerAdvice {

  @ExceptionHandler(
      value = {
        MachineGeneralException.class,
        MachineBalanceException.class,
        UnsupportedTypeException.class
      })
  public ResponseEntity<ErrorResponse> handleMachineGeneralException(MachineGeneralException e) {
    return ResponseEntity.badRequest()
        .body(ErrorResponse.builder().message(e.getMessage()).build());
  }

  @ExceptionHandler(value = ItemNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleItemNotFoundException(ItemNotFoundException e) {
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ErrorResponse> handleAnyOtherException(Exception e) {
    var message =
        e.getMessage() == null ? "Something went wrong please check server logs" : e.getMessage();
    return ResponseEntity.badRequest().body(ErrorResponse.builder().message(message).build());
  }
}
