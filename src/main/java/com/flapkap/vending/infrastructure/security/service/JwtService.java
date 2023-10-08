package com.flapkap.vending.infrastructure.security.service;

public interface JwtService {

  String getUsernameFromToken(String token);

  String generateToken(String username);
}
