package com.dev.cinema.security;

import com.dev.cinema.model.User;
import com.dev.cinema.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with the email "
                        + email + " was not found"));
        org.springframework.security.core.userdetails.User.UserBuilder builder =
                org.springframework.security.core.userdetails.User.withUsername(email);
        builder.password(user.getPassword());
        String[] userRoles = user.getRoles().stream()
                .map(r -> r.getRoleName().toString())
                .toArray(String[]::new);
        builder.roles(userRoles);
        return builder.build();
    }
}
