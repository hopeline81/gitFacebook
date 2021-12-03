package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.Role;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.service.contrack.ChangeUserEmailService;
import com.example.facebookdemo.service.contrack.UserService;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UpdateProfileServiceImplTest {

    @Mock private UserService userService;
    @Mock private ProfileRepository profileRepository;
    @Mock private ChangeUserEmailService changeUserEmailService;
    private UpdateProfileServiceImpl updateProfileServiceImplTest;
    private User user;
    private Profile profile;

    @BeforeEach
    void setUp() {
        updateProfileServiceImplTest = new UpdateProfileServiceImpl(userService,
                profileRepository,
                changeUserEmailService);
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
    }

    @Test
    void updateProfileDetails() {

        
    }

    @Test
    void changeEmail() {
    }
}