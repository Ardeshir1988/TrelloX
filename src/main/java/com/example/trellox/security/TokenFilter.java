package com.example.trellox.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        addJwtInfoInSecurityContext(request);
        filterChain.doFilter(request, response);
    }

    private void addJwtInfoInSecurityContext(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(headerAuth.substring(7)));
        }
    }

    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(extractEmail(token), null, extractGrantedAuthorities(token));
    }

    private Collection<GrantedAuthority> extractGrantedAuthorities(String token) {
        return jwtUtils.getRolesFromJwtToken(token).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private String extractEmail(String token) {
        return jwtUtils.getEmailFromToken(token);
    }
}

