package com.example.facebookdemo.entity;

import javax.persistence.*;

@Entity
@Table(name = "friends_requests")
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = User.class, optional = false)
    private User requesterUser;

    @ManyToOne(targetEntity = User.class, optional = false)
    private User requestedUsers;

    @Enumerated(EnumType.STRING)
    private FriendRequestStatus status;

    public FriendRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRequesterUser() {
        return requesterUser;
    }

    public void setRequesterUser(User user) {
        this.requesterUser = user;
    }

    public User getRequestedUsers() {
        return requestedUsers;
    }

    public void setRequestedUsers(User requestedUsers) {
        this.requestedUsers = requestedUsers;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }
}
