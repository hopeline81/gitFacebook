package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.FriendRequestDTO;
import com.example.facebookdemo.entity.FriendRequest;
import com.example.facebookdemo.entity.FriendRequestStatus;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.FriendRequestRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.FriendRequestService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestServiceImpl(UserRepository userRepository, FriendRequestRepository friendRequestRepository) {
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
    }

    @Override
    public FriendRequest sendFriendRequest(User user, String firstName, String lastName) {
        User requestedUser = userRepository.findUserByFirstNameAndLastName(firstName, lastName);
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setStatus(FriendRequestStatus.PENDING);
        friendRequest.setRequesterUser(user);
        friendRequest.setRequestedUsers(requestedUser);
        return friendRequestRepository.save(friendRequest);
    }

    public Set<FriendRequest> findRequestToUser(User user) {
        return friendRequestRepository.findAllByRequestedUsers(user);
    }

}
