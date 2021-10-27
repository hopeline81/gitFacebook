package com.example.facebookdemo.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageDTO {

    private MultipartFile image;

    private String description;

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
}
