package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
 List<Image> findAllByUser(User user);
}
