package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    private final UserRepository userRepositoryUnderTest;
    private User user;

    @Autowired
    UserRepositoryTest(UserRepository userRepositoryUnderTest) {
        this.userRepositoryUnderTest = userRepositoryUnderTest;
    }

    @BeforeEach
    public void init() {
        this.user = new User();
        String email = "hopeliness@yahoo.com";
        user.setId(1L);
        user.setFirstName("Nadezhda");
        user.setLastName("Vacheva");
        user.setUsername("Nadezhda", "Vacheva");
        user.setEmail(email);
        user.setPassword("aaaaa");
        user.setAge(Integer.valueOf("40"));
        user.setRegisterDate(LocalDateTime.parse("2021-11-22T14:23:14"));

        Profile profile = new Profile();
        profile.setAge("40");
        profile.setAddress("Vratitsa 36A");
        profile.setId(1L);
        profile.setEmail(email);
        user.setProfile(profile);

        String randomCode = "81wdXmqxAWDSjLlh8XDWtJJRaVOU2sq7";
        user.setVerificationCode(randomCode);
        userRepositoryUnderTest.save(user);
    }


    @Test
    void isFindsUserByEmail() {
        String email = user.getEmail();
        User expectedUser = userRepositoryUnderTest.findUserByEmail(email);

        assertThat(expectedUser).isNotNull();
    }

    @Test
    void isFindsUserByResetPasswordToken() {
    }

    @Test
    void isFindsUserById() {
        Long id = user.getId();
        User expectedUser = userRepositoryUnderTest.findUserById(id).get();

        assertThat(expectedUser).isNotNull();
    }

    @Test
    void isFindsByVerificationCode() {
        String verificationCode = user.getVerificationCode();
        User expectedUser = userRepositoryUnderTest.findByVerificationCode(verificationCode);

        assertThat(expectedUser).isNotNull();
    }

    @Test
    void searchByNameAndSort() {
    }
}