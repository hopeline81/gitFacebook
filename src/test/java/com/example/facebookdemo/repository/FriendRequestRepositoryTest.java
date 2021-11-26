package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.FriendRequest;
import com.example.facebookdemo.entity.FriendRequestStatus;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FriendRequestRepositoryTest {

    private final FriendRequestRepository friendRequestRepositoryUnderTest;
    private final UserRepository userRepository;
    private Set<FriendRequest> friendRequests;
    private User user;
    private User user1;

    @Autowired
    FriendRequestRepositoryTest(FriendRequestRepository friendRequestRepositoryUnderTest, UserRepository userRepository) {
        this.friendRequestRepositoryUnderTest = friendRequestRepositoryUnderTest;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void init() {
        this.user = new User();
        user.setId(1L);
        user.setFirstName("Nadezhda");
        user.setLastName("Vacheva");
        user.setUsername("Nadezhda", "Vacheva");
        user.setEmail("hopeliness@yahoo.com");
        user.setPassword("aaaaa");
        user.setAge(Integer.valueOf("40"));
        user.setRegisterDate(LocalDateTime.parse("2021-11-22T14:23:14"));
        user.setVerificationCode("81wdXmqxAWDSjLlh8XDWtJJRaVOU2sq7");

        Profile profile = new Profile();
        profile.setAge("40");
        profile.setAddress("Vratitsa 36A");
        profile.setId(1L);
        profile.setEmail("hopeliness@yahoo.com");
        user.setProfile(profile);
        userRepository.save(user);

        String emailUser1 = "ddddd@mail.bg";
        this.user1 = new User();
        user1.setId(3L);
        user1.setFirstName("Wally");
        user1.setLastName("Parker");
        user1.setUsername("Wally", "Parker");
        user1.setEmail(emailUser1);
        user1.setPassword("ddddd");
        user1.setAge(Integer.valueOf("22"));
        user1.setRegisterDate(LocalDateTime.parse("2021-11-22T14:26:14"));

        Profile profile1 = new Profile();
        profile1.setAge("22");
        profile1.setAddress("Vratitsa 36A");
        profile1.setId(2L);
        profile1.setEmail(emailUser1);
        user1.setProfile(profile1);

        String randomCodeUser1 = "81wdXmqxAWDSjLlh8FREtJJRaVOU2sq7";
        user1.setVerificationCode(randomCodeUser1);
        userRepository.save(user1);

        FriendRequest firstFriendRequest = new FriendRequest();
        firstFriendRequest.setId(4L);
        firstFriendRequest.setStatus(FriendRequestStatus.PENDING);
        firstFriendRequest.setRequesterUser(user1);
        firstFriendRequest.setRequestedUsers(user);

        this.friendRequests = new HashSet<>();
        friendRequests.add(firstFriendRequest);

        user.setUserRequests(friendRequests);
        userRepository.save(user);
//        user1.setUserRequests(friendRequests);
        friendRequestRepositoryUnderTest.save(firstFriendRequest);

    }

    @Test
    void isFindsAllByRequestedUsers() {
        Set<FriendRequest> allRequestsBYUser = friendRequestRepositoryUnderTest.findAllByRequestedUsers(user);

        assertThat(allRequestsBYUser.size()).isEqualTo(1);
    }

    @Test
    void findFriendRequestByRequesterUserAndRequestedUsers() {
    }

    @Test
    void findAllByStatusACCEPTED() {
    }
}