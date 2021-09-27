package com.example.facebookdemo.entity;

import javax.persistence.*;
import java.awt.*;
import java.util.Set;

@Entity
@Table(name = "user_profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "age")
    private String age;

//    @OneToOne(targetEntity = Image.class, optional = false)
//    private Image userImage;

    public Profile() {
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

//    public Image getUserImage() {
//        return userImage;
//    }
//
//    public void setUserImage(Image userImage) {
//        this.userImage = userImage;
//    }
}
