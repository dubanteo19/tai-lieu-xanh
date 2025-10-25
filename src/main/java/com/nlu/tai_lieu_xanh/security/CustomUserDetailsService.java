package com.nlu.tai_lieu_xanh.security;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nlu.tai_lieu_xanh.repository.UserRepository;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userRepository.findUserByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    return new CustomUserDetails(user);
  }
}
