package com.example.facebookdemo.dto;

public class LoginDTO {
    private String email;
    private String password;

    public LoginDTO() {
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

