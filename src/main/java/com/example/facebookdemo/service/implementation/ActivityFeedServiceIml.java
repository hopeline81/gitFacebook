package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.ActivityFeedDTO;
import com.example.facebookdemo.dto.ImageDTO;
import com.example.facebookdemo.dto.ImageDTOResponse;
import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ImageRepository;
import com.example.facebookdemo.repository.PostRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.ImageUploadService;
import com.example.facebookdemo.service.contrack.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityFeedServiceIml {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;
    private final PostService postService;
    private final ImageUploadService imageService;

    @Autowired
    public ActivityFeedServiceIml(UserRepository userRepository, ImageRepository imageRepository, PostRepository postRepository, PostService postService, ImageUploadService imageService) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.postRepository = postRepository;
        this.postService = postService;
        this.imageService = imageService;
    }

    public ActivityFeedDTO showActivityFeedDTO(User user) {

        List<PostDTO> allUserPosts = user.getPosts().stream()
                .filter(post -> post.getParent() == null)
                .map(postService::convertToDTO)
                .collect(Collectors.toList());

        List<PostDTO> allFriendPosts = user.getFriends().stream()
                .flatMap(friend -> friend.getPosts().stream())
                .filter(post -> post.getParent() == null)
                .map(postService::convertToDTO)
                .collect(Collectors.toList());

        List<ImageDTOResponse> allUserImages = user.getImages().stream()
                .map(imageService::convertImageToImageDTOResponse)
                .collect(Collectors.toList());

        List<ImageDTOResponse> allFriendsImages = user.getFriends().stream()
                .flatMap(friend -> friend.getImages().stream())
                .map(imageService::convertImageToImageDTOResponse)
                .collect(Collectors.toList());


        ActivityFeedDTO activityFeedDTO = new ActivityFeedDTO();
        activityFeedDTO.setUserPosts(allUserPosts);
        activityFeedDTO.setFriendPosts(allFriendPosts);
        activityFeedDTO.setUserImages(allUserImages);
        activityFeedDTO.setFriendImages(allFriendsImages);

        return activityFeedDTO;
    }
}
