package com.example.facebookdemo.repository;

import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import org.assertj.core.internal.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ImageRepositoryTest {

    private final ImageRepository imageRepositoryUnderTest;
    private final UserRepository userRepository;
    private List<Image> images;
    private User user;

    @Autowired
    ImageRepositoryTest(ImageRepository imageRepositoryUnderTest, UserRepository userRepository) {
        this.imageRepositoryUnderTest = imageRepositoryUnderTest;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void addImagesToUser() {
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

        Image firstImage = new Image();
        firstImage.setId(1L);
        firstImage.setImageUploadDate(LocalDateTime.parse("2021-11-22T14:35:48"));
        firstImage.setUser(user);
        firstImage.setImageUrl("1637584547644-bird-g432e12474_640.png");
        firstImage.setNumberOfLikesImage(3);
        firstImage.setDescription("Nice");
        firstImage.setNumberOfLikesImage(4);

        Image secondImage = new Image();
        secondImage.setId(2L);
        secondImage.setImageUploadDate(LocalDateTime.parse("2021-11-22T16:35:48"));
        secondImage.setUser(user);
        secondImage.setImageUrl("1637584587644-bird-g432e12474_640.png");
        secondImage.setNumberOfLikesImage(3);
        secondImage.setDescription("Bird");
        secondImage.setNumberOfLikesImage(3);

        this.images = new ArrayList<>();
        images.add(firstImage);
        images.add(secondImage);

        user.setImages(images);
        userRepository.save(user);
    }
    @Test
    void isFindsAllImages() {
        List<Image> expectedAllImages = imageRepositoryUnderTest.findAll();

        assertThat(expectedAllImages.size()).isEqualTo(2);
    }
}