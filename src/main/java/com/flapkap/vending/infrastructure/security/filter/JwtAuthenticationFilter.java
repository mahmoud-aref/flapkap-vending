package com.flapkap.vending.infrastructure.security.filter;

import com.flapkap.vending.domain.user.service.UserService;
import com.flapkap.vending.infrastructure.security.service.JwtService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Order(1)
public class JwtAuthenticationFilter implements Filter {

    private final JwtService jwtService;
    private final UserService userService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        final String authHeader = httpRequest.getHeader("Authorization");
        if (!StringUtils.hasLength(authHeader)
                || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
            chain.doFilter(request, response);
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
        chain.doFilter(request, response);
    }


}
