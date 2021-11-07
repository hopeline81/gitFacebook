package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.entity.FriendRequestStatus;
import com.example.facebookdemo.entity.*;
import com.example.facebookdemo.repository.FriendRequestRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.FriendRequestService;
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

    @Override
    public User findRequesterUser(Long userId) {
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
    public FriendRequest findRequest(User newFriend, User user) {
        return friendRequestRepository.findFriendRequestByRequesterUserAndRequestedUsers(newFriend, user);
    }

    @Override
    public User addNewFriend(User user, User newFriend) {

        Set<User> userFriends = user.getFriends();
        if(userFriends.contains(newFriend)){
            throw new IllegalArgumentException("This friend is already added");
        }
        userFriends.add(newFriend);
        user.setFriends(userFriends);
        userRepository.save(user);
        return user;
    }

    @Override
    public void changeRequestStatusFromPendingToAccept(User user, User newFriend) {
        FriendRequest friendRequest = findRequest(newFriend, user);
        if(friendRequest == null) {
            throw new IllegalArgumentException("Request doesn't exist");
        }
        friendRequest.setStatus(FriendRequestStatus.ACCEPTED);
    }

    @Override
    public List<User> getFriends(User user) {
        Set<FriendRequest> allUserRequests = findRequestToUser(user);

        return allUserRequests.stream()
                .filter(friendRequest -> friendRequest.getStatus().equals(FriendRequestStatus.ACCEPTED))
                .map(FriendRequest::getRequesterUser)
                .collect(Collectors.toList());
    }
}
