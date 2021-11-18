package com.example.facebookdemo.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "urls")
    private String imageUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "likes_image")
    private Integer numberOfLikesImage;

    @ManyToOne(targetEntity = User.class, optional = false)
    private User user;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "liked_images_users",
            joinColumns = {@JoinColumn(name = "image_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> usersLikes;

    public Image() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getNumberOfLikesImage() {
        return numberOfLikesImage;
    }

    public void setNumberOfLikesImage(Integer numberOfLikesImage) {
        this.numberOfLikesImage = numberOfLikesImage;
    }

    public List<User> getUsersLikes() {
        return usersLikes;
    }

    public void setUsersLikes(List<User> usersLikes) {
        this.usersLikes = usersLikes;
    }
}