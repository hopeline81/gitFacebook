package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.FriendRequest;
import com.example.facebookdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    Set<FriendRequest> findAllByRequesterUser(User user);

    Set<FriendRequest> findAllByRequestedUsers(User user);

    FriendRequest findFriendRequestByRequesterUserAndRequestedUsers(User requesterUser, User requestedUser);

    @Query("SELECT u from FriendRequest AS u " +
            "WHERE u.status = com.example.facebookdemo.entity.FriendRequestStatus.ACCEPTED")
    Optional<FriendRequest> findAllByStatusACCEPTED(User user);
}
