package com.example.facebookdemo.entity;

import javax.persistence.*;

@Entity
@Table(name = "friends")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = User.class, optional = false)
    private User user;

    public Friend() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
