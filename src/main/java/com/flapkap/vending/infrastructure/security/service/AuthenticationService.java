package com.flapkap.vending.infrastructure.security.service;

import com.flapkap.vending.infrastructure.security.model.JWTResponse;
import com.flapkap.vending.infrastructure.security.model.UserLoginRequest;
import com.flapkap.vending.infrastructure.security.model.UserRegistrationRequest;

public interface AuthenticationService {
  JWTResponse registerUser(UserRegistrationRequest request);

  JWTResponse authenticateUser(UserLoginRequest request);
}
