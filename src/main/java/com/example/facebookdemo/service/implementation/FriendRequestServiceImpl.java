package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.FriendRequestDTO;
import com.example.facebookdemo.entity.FriendRequestStatus;
import com.example.facebookdemo.entity.*;
import com.example.facebookdemo.repository.FriendRequestRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.FriendRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FriendRequestServiceImpl(UserRepository userRepository, FriendRequestRepository friendRequestRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public FriendRequest sendFriendRequest(User user, String requestedId) {
        User requestedUser = findRequestedUser(Long.valueOf(requestedId));
        Set<FriendRequest> allRequest = requestedUser.getUserRequests();
        if(allRequest != null) {
           FriendRequest friendRequest =  friendRequestRepository.findFriendRequestByRequesterUserAndRequestedUsers(user, requestedUser);
           if(friendRequest != null) {
               throw new IllegalArgumentException("Already exist");
           }
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setRequesterUser(user);
        friendRequest.setRequestedUsers(requestedUser);
        friendRequest.setStatus(FriendRequestStatus.PENDING);

        return friendRequestRepository.save(friendRequest);
    }

    public List<FriendRequestDTO> findRequestToUser(User user) {
        return friendRequestRepository.findAllByRequestedUsers(user)
                .stream()
                .map(friendRequest -> modelMapper.map(friendRequest, FriendRequestDTO.class))
                .collect(Collectors.toList());
    }

    public User findRequestedUser(Long userId) {
        Optional<User> user = userRepository.findUserById(userId);
        User existingUser;
        if(user.isPresent()) {
            existingUser = user.get();
        } else {
            throw new IllegalArgumentException();
        }
        return existingUser;
    }

    @Override
    public User findRequesterUserById(Long userId) {
        Optional<User> user = userRepository.findUserById(userId);
        User existingUser;
        if(user.isPresent()) {
            existingUser = user.get();
        } else {
            throw new IllegalArgumentException();
        }
        return existingUser;
    }

    @Override
    public Set<FriendRequest> findAllRequestToUser(User user) {
        User user1 = userRepository.findUserByEmail(user.getEmail());

        return user1.getUserRequests();
    }

    @Override
    public FriendRequest findRequest(User newFriend, User user) {

        return friendRequestRepository.findFriendRequestByRequesterUserAndRequestedUsers(newFriend, user);
    }

    @Override
    public void changeRequestStatusFromPendingToAccept(User user, User newFriend) {
        FriendRequest friendRequest = findRequest(newFriend, user);
        if(friendRequest == null) {
            throw new IllegalArgumentException("Request doesn't exist");
        }
        friendRequest.setStatus(FriendRequestStatus.ACCEPTED);
    }
}
