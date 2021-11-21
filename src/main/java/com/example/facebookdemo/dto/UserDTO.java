package com.example.facebookdemo.dto;

import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Post;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDTO {

    @NotBlank
    private  String firstName;

    @NotBlank
    private  String lastName;

    private  String username;

    @NotBlank
    private  String email;

    @NotBlank
    private String address;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordRepeat;

    @NotNull(message = "Age can't under 14")
    @Min(14)
    private Integer age;

    public UserDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String firstName, String lastName) {
        this.username = firstName + lastName;
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
