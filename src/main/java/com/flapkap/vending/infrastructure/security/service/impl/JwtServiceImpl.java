package com.flapkap.vending.infrastructure.security.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.flapkap.vending.infrastructure.security.exceptions.UnauthorizedException;
import com.flapkap.vending.infrastructure.security.service.JwtService;
import java.time.LocalDate;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

  @Value("${jwt.signing.key}")
  private String jwtSigningKey;

  @Override
  public String getUsernameFromToken(String token) {
    return validateTokenAndReturnSubject(token);
  }

  @Override
  public String generateToken(String username) {
    Algorithm algorithm = Algorithm.HMAC256(this.jwtSigningKey);

    return JWT.create()
        .withIssuer("flapkap")
        .withSubject(username)
        .withIssuedAt(new Date(LocalDate.now().toEpochDay()))
        .withExpiresAt(new Date(LocalDate.now().plusDays(1).toEpochDay()))
        .sign(algorithm);
  }

  private String validateTokenAndReturnSubject(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(this.jwtSigningKey);
      JWTVerifier verifier = JWT.require(algorithm).build();
      DecodedJWT decodedJWT = verifier.verify(token);
      return decodedJWT.getSubject();
    } catch (Exception e) {
      throw new UnauthorizedException("Invalid token");
    }
  }
}
