package com.flapkap.vending.infrastructure.security.filter;

import com.flapkap.vending.domain.user.service.UserService;
import com.flapkap.vending.infrastructure.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserService userService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");
    if (StringUtils.hasLength(authHeader)
        || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    final var token = authHeader.substring(7);
    final var username = jwtService.getUsernameFromToken(token);
    final var user = userService.loadUserByUsername(username);

    var securityContext = SecurityContextHolder.createEmptyContext();
    var authenticationToken =
        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

    securityContext.setAuthentication(authenticationToken);
    SecurityContextHolder.setContext(securityContext);
    filterChain.doFilter(request, response);
  }
}
