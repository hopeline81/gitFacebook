package com.example.facebookdemo.dto;

import com.example.facebookdemo.entity.FriendRequestStatus;
import com.example.facebookdemo.entity.User;

import java.time.LocalDateTime;

public class FriendRequestDTO {

    private User requesterUser;

    private User requestedUser;

    private FriendRequestStatus status;

    public FriendRequestDTO() {
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }

    public User getRequesterUser() {
        return requesterUser;
    }

    public void setRequesterUser(User requesterUser) {
        this.requesterUser = requesterUser;
    }

    public User getRequestedUser() {
        return requestedUser;
    }

    public void setRequestedUser(User requestedUser) {
        this.requestedUser = requestedUser;
    }
}
