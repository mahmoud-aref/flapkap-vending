package com.flapkap.vending.application.user.controller;

import com.flapkap.vending.infrastructure.security.model.JWTResponse;
import com.flapkap.vending.infrastructure.security.model.UserLoginRequest;
import com.flapkap.vending.infrastructure.security.model.UserRegistrationRequest;
import com.flapkap.vending.infrastructure.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserAuthenticationController.USER_AUTHENTICATION_PATH)
@RequiredArgsConstructor
public class UserAuthenticationController {

  public static final String USER_AUTHENTICATION_PATH = "/vending/api/v1/auth";
  public static final String USER_REGISTRATION_PATH_REGISTER = "/register";
  public static final String USER_REGISTRATION_PATH_LOGIN = "/login";

  private final AuthenticationService authenticationService;

  @PostMapping(USER_REGISTRATION_PATH_REGISTER)
  public ResponseEntity<JWTResponse> registerUser(@RequestBody UserRegistrationRequest request) {
    return ResponseEntity.ok(authenticationService.registerUser(request));
  }

  @PostMapping(USER_REGISTRATION_PATH_LOGIN)
  public ResponseEntity<JWTResponse> authenticateUser(@RequestBody UserLoginRequest request) {
    return ResponseEntity.ok(authenticationService.authenticateUser(request));
  }
}
