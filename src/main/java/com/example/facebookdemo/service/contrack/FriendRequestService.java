package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.FriendRequestDTO;
import com.example.facebookdemo.entity.FriendRequest;

public interface FriendRequestService {
    FriendRequest sendFriendRequest(FriendRequestDTO friendRequestDTO, String firstName, String lastName);
}
