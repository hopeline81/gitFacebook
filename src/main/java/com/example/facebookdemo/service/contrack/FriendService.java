package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.entity.User;

import java.util.Set;

public interface FriendService {
    void addNewFriend(User user, Long newFriendId);
    Set<User> getFriends(User user);
}
