package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.FriendRequestDTO;
import com.example.facebookdemo.entity.FriendRequest;
import com.example.facebookdemo.entity.User;

import java.util.Set;

public interface FriendRequestService {
    FriendRequest sendFriendRequest(User requestedUser, String firstName, String lastName);

    Set<FriendRequest> findRequestToUser(User user);
}
