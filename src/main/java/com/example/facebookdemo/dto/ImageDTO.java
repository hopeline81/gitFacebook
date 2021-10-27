package com.example.facebookdemo.dto;

import com.example.facebookdemo.entity.User;
import org.springframework.web.multipart.MultipartFile;

public class ImageDTO {

    private MultipartFile image;

    private String description;

    private User user;

    public ImageDTO() {
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
