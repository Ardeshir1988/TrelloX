package com.example.trellox.security;

import com.example.trellox.enums.Role;
import com.example.trellox.model.Customer;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${trellox.jwtSecret}")
    private String jwtSecret;
    @Value("${trellox.jwtExpiration}")
    private int jwtExpiration;

    public String generateToken(Customer customer) {
        return Jwts.builder()
                .setClaims(createClaims(customer.getEmail(),(List<Role>) customer.getRoles()))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private Claims createClaims(String email,List<Role> roles) {
        Claims claims = new DefaultClaims()
                .setSubject(email)
                .setIssuer("trellox")
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration));
        claims.put("roles", roles.stream().map(Enum::name).collect(Collectors.joining(",")));
        return claims;
    }

    public List<String> getRolesFromJwtToken(String token) {
        validateJwtToken(token);
        String roles = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("roles").toString();
        if (roles.contains(",")) {
            return Arrays.stream(roles.split(",")).collect(Collectors.toList());
        } else
            return List.of(roles);
    }

    public void validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token is expired");
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException("Token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Claims string is empty");
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("sub").toString();
    }
}
