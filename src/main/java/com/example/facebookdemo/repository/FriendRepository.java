package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findById(Long friendId);
}
