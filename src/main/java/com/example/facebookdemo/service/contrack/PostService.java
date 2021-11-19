package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;

import java.text.ParseException;
import java.util.List;

public interface PostService {
    Post save(PostDTO postDTO, User user);
    PostDTO convertPostToPostDTO(Post postCreated);
    PostDTO getPostById(Long id);
    void createLike(Post post,Long userId);
    void addComment(Long parentPostId, Post post, Long userId);
    Post convertCommentDTOToEntity(PostDTO postDTO);
    List<PostDTO> findAllCommentsToCurrentPost(Long postId);
    List<PostDTO> getUserAndFriendPostDTOS(User user1);
}
