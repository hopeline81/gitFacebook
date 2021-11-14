package com.example.facebookdemo.dto;

import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class PostDTO {

    private Long id;

    private String text;

    private LocalDateTime postDate;

    private User user;

    private List<User> usersLikes;

    private Integer numberOfLikes;

    private List<Post> comments;

    public PostDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(Integer numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public List<User> getUsersLikes() {
        return usersLikes;
    }

    public void setUsersLikes(List<User> usersLikes) {
        this.usersLikes = usersLikes;
    }

    public List<Post> getComments() {
        return comments;
    }

    public void setComments(List<Post> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
