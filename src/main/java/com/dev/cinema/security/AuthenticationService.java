package com.dev.cinema.security;

import com.dev.cinema.model.Role;
import com.dev.cinema.model.User;
import java.util.Set;

public interface AuthenticationService {
    User register(String email, String password, Set<Role> roles);
}
