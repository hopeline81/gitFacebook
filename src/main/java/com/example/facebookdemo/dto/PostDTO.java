package com.example.facebookdemo.dto;

import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.User;

import java.time.LocalDateTime;
import java.util.Set;

public class PostDTO {

    private  String text;

    private LocalDateTime postDate;

    private User user;

    private Image images;

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
