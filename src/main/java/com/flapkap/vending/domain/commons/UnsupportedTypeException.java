package com.flapkap.vending.domain.commons;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedTypeException extends RuntimeException {
  public UnsupportedTypeException(String message) {
    super(message);
  }
}
