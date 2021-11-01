package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);
    User findByResetPasswordToken(String token);

    User findUserByEmail(String email);

    User findByVerificationCode(String code);

    @Query("SELECT u FROM User AS u WHERE (u.firstName like ?1% OR u.lastName like ?1%)")
    List<User> searchByNameAndSort(String name, Sort sort);
}