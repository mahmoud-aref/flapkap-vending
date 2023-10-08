package com.flapkap.vending.infrastructure.security.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JWTResponse {
  private String token;
}
