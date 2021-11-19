package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;

import java.util.List;

public interface PostService {

    Post save(PostDTO postDTO, User user);
    PostDTO convertPostToPostDTO(Post postCreated);
    PostDTO getPostById(Long id);
    void createLike(Post post,Long userId);
    List<PostDTO> getUserAndFriendPostDTOS(User user1);
    Post convertPostDTOToEntity(PostDTO postDTO);
}
