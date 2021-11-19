package com.example.facebookdemo.dto;

import lombok.Data;

import java.util.List;


public @Data
class ActivityFeedDTO {

    private List<PostDTO> friendPosts;

    private List<PostDTO> userPosts;

    private List<ImageResponseDTO> friendImages;

    private List<ImageResponseDTO> userImages;

    public ActivityFeedDTO() {
    }

    public List<PostDTO> getFriendPosts() {
        return friendPosts;
    }

    public void setFriendPosts(List<PostDTO> friendPosts) {
        this.friendPosts = friendPosts;
    }

    public List<PostDTO> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(List<PostDTO> userPosts) {
        this.userPosts = userPosts;
    }

    public List<ImageResponseDTO> getFriendImages() {
        return friendImages;
    }

    public void setFriendImages(List<ImageResponseDTO> friendImages) {
        this.friendImages = friendImages;
    }

    public List<ImageResponseDTO> getUserImages() {
        return userImages;
    }

    public void setUserImages(List<ImageResponseDTO> userImages) {
        this.userImages = userImages;
    }

    @Override
    public String toString() {
        return "ActivityFeedDTO{" +
                "friendPosts=" + friendPosts +
                ", userPosts=" + userPosts +
                ", friendImages=" + friendImages +
                ", userImages=" + userImages +
                '}';
    }
}
