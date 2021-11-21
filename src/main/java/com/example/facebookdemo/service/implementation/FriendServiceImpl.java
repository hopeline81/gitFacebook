package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FriendServiceImpl implements FriendService {

    private final UserRepository userRepository;

    @Autowired
    public FriendServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addNewFriend(User user, Long newFriendId) {
        Set<User> userFriends = user.getFriends();
        User newFriend = userRepository.findUserById(newFriendId).get();
        if(userFriends.contains(newFriend)){
            throw new IllegalArgumentException("This friend is already added");
        }
        userFriends.add(newFriend);
        user.setFriends(userFriends);

        Set<User> friendsNewFriends = newFriend.getFriends();
        if(friendsNewFriends.contains(user)){
            throw new IllegalArgumentException("This friend is already added");
        }
        friendsNewFriends.add(user);
        newFriend.setFriends(friendsNewFriends);
        userRepository.save(user);
    }

    @Override
    public Set<User> getFriends(User user) {

        return user.getFriends();
    }
}
