package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.ActivityFeedDTO;
import com.example.facebookdemo.dto.ImageResponseDTO;
import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.ImageUploadService;
import com.example.facebookdemo.service.contrack.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityFeedServiceIml {

    private final PostService postService;
    private final ImageUploadService imageService;

    @Autowired
    public ActivityFeedServiceIml(PostService postService, ImageUploadService imageService) {
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

        List<ImageResponseDTO> allUserImages = user.getImages().stream()
                .map(imageService::convertImageToImageDTOResponse)
                .collect(Collectors.toList());

        List<ImageResponseDTO> allFriendsImages = user.getFriends().stream()
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
