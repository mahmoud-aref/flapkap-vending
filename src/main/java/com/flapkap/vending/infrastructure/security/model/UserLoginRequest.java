package com.flapkap.vending.infrastructure.security.model;

import lombok.Data;

@Data
public class UserLoginRequest {
  private String username;
  private String password;
}
