package com.dev.cinema.controllers;

import com.dev.cinema.model.dto.UserRequestDto;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.RoleService;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final RoleService roleService;

    public AuthenticationController(AuthenticationService authenticationService,
                                    RoleService roleService) {
        this.authenticationService = authenticationService;
        this.roleService = roleService;
    }

    @PostMapping("/registration")
    public void registerUser(@RequestBody @Valid UserRequestDto dto) {
        authenticationService.register(dto.getEmail(), dto.getPassword(),
                Set.of(roleService.getRoleByName("USER")));
    }
}
