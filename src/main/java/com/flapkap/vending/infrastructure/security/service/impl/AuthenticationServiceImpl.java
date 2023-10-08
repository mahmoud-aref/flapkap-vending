package com.flapkap.vending.infrastructure.security.service.impl;

import com.flapkap.vending.infrastructure.security.model.Role;
import com.flapkap.vending.domain.user.model.UserEntity;
import com.flapkap.vending.infrastructure.security.model.JWTResponse;
import com.flapkap.vending.infrastructure.security.model.UserLoginRequest;
import com.flapkap.vending.infrastructure.security.model.UserRegistrationRequest;
import com.flapkap.vending.infrastructure.security.service.AuthenticationService;
import com.flapkap.vending.infrastructure.security.service.JwtService;
import com.flapkap.vending.infrastructure.user.datasource.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public JWTResponse registerUser(UserRegistrationRequest request) {

    var user =
        UserEntity.builder()
            .fullName(request.getName())
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.valueOf(request.getRole()))
            .build();
    userRepository.save(user);
    var jwtToken = jwtService.generateToken(user.getUsername());

    return JWTResponse.builder().token(jwtToken).build();
  }

  @Override
  public JWTResponse authenticateUser(UserLoginRequest request) {

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    var user =
        userRepository
            .findByUsername(request.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    var jwtToken = jwtService.generateToken(user.getUsername());

    return JWTResponse.builder().token(jwtToken).build();
  }
}
