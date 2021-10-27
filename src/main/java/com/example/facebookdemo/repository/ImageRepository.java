package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
 List<Image> findAll();
}
