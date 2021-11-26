package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    private final PostRepository postRepositoryUnderTest;
    private final UserRepository userRepository;
    private List<Post> posts;
    private User user;

    @Autowired
    PostRepositoryTest(PostRepository postRepositoryUnderTest, UserRepository userRepository) {
        this.postRepositoryUnderTest = postRepositoryUnderTest;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void createPosts() {
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

        Post firstPost = new Post();
        firstPost.setId(1L);
        firstPost.setTextPost("Hello");
        firstPost.setPostDate(LocalDateTime.parse("2021-11-22T14:35:07"));
        firstPost.setNumberOfLikes(0);
        firstPost.setUser(user);

        Post secondPost = new Post();
        secondPost.setId(2L);
        secondPost.setTextPost("Hello");
        secondPost.setPostDate(LocalDateTime.parse("2021-11-22T14:35:07"));
        secondPost.setNumberOfLikes(0);
        secondPost.setUser(user);

        Post thirdPost = new Post();
        thirdPost.setId(3L);
        thirdPost.setTextPost("Nice day");
        thirdPost.setPostDate(LocalDateTime.parse("2021-11-22T15:35:07"));
        thirdPost.setNumberOfLikes(0);
        thirdPost.setUser(user);

        this.posts = new ArrayList<>();
        posts.add(firstPost);
        posts.add(secondPost);
        posts.add(thirdPost);

        user.setPosts(posts);
        userRepository.save(user);
    }

    @Test
    void isFindsAllPosts() {
        List<Post> expectedPosts = postRepositoryUnderTest.findAll();

        assertThat(expectedPosts.size()).isEqualTo(3);
    }

    @Test
    void isFindsPostById() {
        Long postId = 1L;
        Post expectedPost = postRepositoryUnderTest.findById(postId).get();

        assertThat(expectedPost).isNotNull();
    }
}