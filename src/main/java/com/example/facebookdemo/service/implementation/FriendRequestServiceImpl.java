package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.entity.*;
import com.example.facebookdemo.repository.FriendRepository;
import com.example.facebookdemo.repository.FriendRequestRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.FriendRequestService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;

    public FriendRequestServiceImpl(UserRepository userRepository, FriendRequestRepository friendRequestRepository, FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.friendRequestRepository = friendRequestRepository;
        this.friendRepository = friendRepository;
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
    public void addNewFriend(User user, User newFriend) {
        Friend friend = new Friend();
        friend.setId(newFriend.getId());
        friend.setUser(user);
        friendRepository.save(friend);

        Set<User> userFriends = user.getFriends();
//// null is false but size is 0
//        if (userFriends == null) {
//            userFriends = new HashSet<>();
//            userFriends.add(newFriend);
//        }
        if(userFriends.contains(newFriend)){
            throw new IllegalArgumentException("This friend is already added");
        }
        userFriends.add(newFriend);
        user.setFriends(userFriends);

    }

//    Set<Role> roles = new HashSet<>();
//        roles.add(roleService.getUserRole());
//        user.setRoles(roles);
//        userRepository.save(user);

    @Override
    public void changeRequestStatusFromPendingToAccept(User user, User newFriend) {
        FriendRequest friendRequest = findRequest(newFriend, user);
        if(friendRequest == null) {
            throw new IllegalArgumentException("Request doesn't exist");
        }
        friendRequest.setStatus(FriendRequestStatus.ACCEPTED);
    }

}
