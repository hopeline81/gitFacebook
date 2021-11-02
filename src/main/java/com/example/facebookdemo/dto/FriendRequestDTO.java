package com.example.facebookdemo.dto;

import com.example.facebookdemo.entity.FriendRequestStatus;
import com.example.facebookdemo.entity.User;

import java.time.LocalDateTime;

public class FriendRequestDTO {

    private FriendRequestStatus status;

    private User user;

    public FriendRequestDTO() {
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
