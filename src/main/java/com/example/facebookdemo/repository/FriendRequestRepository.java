package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.FriendRequest;
import com.example.facebookdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Set<FriendRequest> findAllByRequesterUser(User user);

    Set<FriendRequest> findAllByRequestedUsers(User user);
}
