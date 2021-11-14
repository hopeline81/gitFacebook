package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;

import java.text.ParseException;
import java.util.List;

public interface PostService {
    Post save(PostDTO postDTO, User user);
    List<Post> allPosts();
    PostDTO convertToDTO(Post postCreated);
    Post convertToEntity(PostDTO postDTO);
    PostDTO getPostById(Long id);
    void createLike(Post post,User authenticatedUser);
}
