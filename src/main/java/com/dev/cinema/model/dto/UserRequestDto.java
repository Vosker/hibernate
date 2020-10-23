package com.dev.cinema.model.dto;

public class UserRequestDto {
    private Long id;
    private String email;
    private String password;
    private String repeatedPassword;

    public UserRequestDto() {
    }

    public UserRequestDto(Long id, String email, String password, String repeatedPassword) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
