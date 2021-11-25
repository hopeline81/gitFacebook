package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Override
    Optional<Profile> findById(Long aLong);
}
