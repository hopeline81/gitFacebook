package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    List<Post> findAll(Sort sort);

    Optional<Post> findFirstById(Long postId);

    Optional<Post> findById(Long postId);
}
