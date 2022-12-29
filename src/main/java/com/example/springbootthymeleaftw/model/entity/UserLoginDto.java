package com.example.springbootthymeleaftw.model.entity;

public class UserLoginDto {

    private String email;
    private String password;

    public UserLoginDto() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
