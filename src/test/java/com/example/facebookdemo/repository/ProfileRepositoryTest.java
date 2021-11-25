package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProfileRepositoryTest {

    private final ProfileRepository profileRepositoryUnderTest;
    private Profile profile;

    @Autowired
    ProfileRepositoryTest(ProfileRepository profileRepositoryUnderTest) {
        this.profileRepositoryUnderTest = profileRepositoryUnderTest;
    }

    @BeforeEach
    public void init() {
        this.profile = new Profile();
        String email = "hopeliness@yahoo.com";
        profile.setAge("40");
        profile.setAddress("Vratitsa 36A");
        profile.setId(1L);
        profile.setEmail(email);
        profileRepositoryUnderTest.save(profile);
    }

    @Test
    void isFindsProfileById() {
        Long profileId = profile.getId();
        Profile expectedProfile = profileRepositoryUnderTest.findById(profileId).get();

        assertThat(expectedProfile).isNotNull();
    }
}