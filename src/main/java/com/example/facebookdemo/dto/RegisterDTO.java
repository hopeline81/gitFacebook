package com.example.facebookdemo.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterDTO {

    @NotBlank(message = "Username must not be blank")
    private  String username;

    @NotBlank(message = "Email must not be blank")
    private  String email;

    @NotBlank
    private String address;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 2, max = 6, message = "Must be at least two, no more than six options")
    private String password;

    @NotBlank
    private String passwordRepeat;

    @NotNull(message = "Age can't under 14")
    @Min(14)
    private Integer age;

    public RegisterDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
