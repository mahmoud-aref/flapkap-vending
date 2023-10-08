package com.flapkap.vending.infrastructure.security.model;

import lombok.Data;

@Data
public class UserRegistrationRequest {
  private String username;
  private String password;
  private String name;
  private String role;
}
