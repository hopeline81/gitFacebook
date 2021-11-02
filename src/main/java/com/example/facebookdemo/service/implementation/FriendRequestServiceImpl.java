package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.FriendRequestDTO;
import com.example.facebookdemo.entity.FriendRequest;
import com.example.facebookdemo.entity.FriendRequestStatus;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.FriendRequestRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.FriendRequestService;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;

    public FriendRequestServiceImpl(UserRepository userRepository, FriendRequestRepository friendRequestRepository) {
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
    }

    @Override
    public FriendRequest sendFriendRequest(FriendRequestDTO friendRequestDTO, String firstName, String lastName) {
        User user = userRepository.findUserByFirstNameAndLastName(firstName, lastName);
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setStatus(FriendRequestStatus.PENDING);
        friendRequest.setUser(user);
        return friendRequestRepository.save(friendRequest);
    }


}
