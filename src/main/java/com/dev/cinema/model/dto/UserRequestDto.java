package com.dev.cinema.model.dto;

import com.dev.cinema.validators.ValidEmail;
import javax.validation.constraints.NotNull;

public class UserRequestDto {
    @ValidEmail
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String repeatedPassword;

    public UserRequestDto() {
    }

    public UserRequestDto(String email, String password, String repeatedPassword) {
        this.email = email;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
}
