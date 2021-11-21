package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.FriendRequestDTO;
import com.example.facebookdemo.entity.FriendRequest;
import com.example.facebookdemo.entity.User;

import java.util.List;
import java.util.Set;

public interface FriendRequestService {
    FriendRequest sendFriendRequest(User requestedUser, String requestedId);
    List<FriendRequestDTO> findRequestToUser(User user);
    FriendRequest findRequest(User newFriend, User user);
    User addNewFriend(User user, Long newFriendId);
    void changeRequestStatusFromPendingToAccept(User user, User newFriend);
    Set<User> getFriends(User user);
    User findRequesterUserById(Long requesterUserId);
}
