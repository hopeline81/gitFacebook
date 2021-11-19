package com.example.facebookdemo.dto;

import com.example.facebookdemo.entity.User;

import java.util.List;

public class ImageResponseDTO {

    private Long id;

    private String url;

    private String description;

    private Long userId;

    private Integer numberOfLikesImage;

    private List<User> usersLikedImage;

    public ImageResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getNumberOfLikesImage() {
        return numberOfLikesImage;
    }

    public void setNumberOfLikesImage(Integer numberOfLikesImage) {
        this.numberOfLikesImage = numberOfLikesImage;
    }

    public List<User> getUsersLikedImage() {
        return usersLikedImage;
    }

    public void setUsersLikedImage(List<User> usersLikedImage) {
        this.usersLikedImage = usersLikedImage;
    }
}
