package com.nlu.tai_lieu_xanh.config.filter;

import com.nlu.tai_lieu_xanh.config.JwtAuthenticationEntryPoint;
import com.nlu.tai_lieu_xanh.config.SecurityConstant;
import com.nlu.tai_lieu_xanh.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtFilter extends OncePerRequestFilter {
  private final JwtUtil jwtUtil;
  private final JwtAuthenticationEntryPoint entryPoint;
  private final UserDetailsService customUserDetailsService;

  private static final AntPathMatcher matcher = new AntPathMatcher();

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    boolean skip =
        SecurityConstant.PUBLIC_ENDPOINTS.stream()
            .anyMatch(pattern -> matcher.match("/api/v1" + pattern, path));
    String logMessage =
        skip
            ? "Skipping JWT filter for public endpoint %s".formatted(path)
            : "Applying JWT filter filter to %s".formatted(path);
    log.info(logMessage);
    return skip;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String username = null;
      String token = null;
      final String authorizationHeader = request.getHeader("Authorization");
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        token = authorizationHeader.substring(7);
        username = jwtUtil.getUsername(token);
      }
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        var userDetails = customUserDetailsService.loadUserByUsername(username);
        if (jwtUtil.validateToken(token)) {
          var authentication =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException ex) {
      log.error("JWT Token has expired: {}", ex.getMessage());
      // 2. Use the entryPoint to send the 401 response immediately
      entryPoint.commence(
          request, response, new AuthenticationServiceException("Token expired", ex));
    } catch (Exception ex) {
      log.error("Authentication error: {}", ex.getMessage());
      entryPoint.commence(
          request, response, new AuthenticationServiceException("Invalid token", ex));
    }
  }
}
