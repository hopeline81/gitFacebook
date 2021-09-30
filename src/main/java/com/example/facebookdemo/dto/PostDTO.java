package com.example.facebookdemo.dto;

import com.example.facebookdemo.entity.User;

import java.time.LocalDateTime;

public class PostDTO {

    private  String text;

    private LocalDateTime postDate;

    private User user;

    public PostDTO(String text) {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
