package com.example.facebookdemo.dto;

import com.example.facebookdemo.entity.User;

public class ResponseImageDTO {

    private String url;

    private String description;

    private Long userId;

    public ResponseImageDTO() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
