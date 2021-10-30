package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    
    Optional<Post> findAllByUser(User user);
}
