package com.nlu.tai_lieu_xanh.utils;

import com.nlu.tai_lieu_xanh.domain.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  private static final long ACCESS_TOKEN_EXPIRATION = 5000  ; // 60 minutes
  private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days

  @Value("${jwt.secret}")
  private String secret;

  public String generateToken(String username, Role role) {
    return Jwts.builder()
        .setSubject(username)
        .claim("role", role.name())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
        .signWith(getKey())
        .compact();
  }

  private SecretKey getKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  // Generate Refresh Token
  public String generateRefreshToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
        .signWith(getKey())
        .compact();
  }

  public Claims parseRefreshToken(String token) {
    return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
  }

  public Claims parseToken(String token) {
    return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
  }

  public String getUsername(String token) {
    return parseToken(token).getSubject();
  }

  public String getRole(String token) {
    return parseToken(token).get("role", String.class);
  }

  public boolean validateRefreshToken(String token) {
    try {
      parseRefreshToken(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean validateToken(String token) {
    try {
      parseToken(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
