package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.Post;

import java.util.List;

public interface CommentService {

    void addComment(Long parentPostId, Post post, Long userId);
    Post convertCommentDTOToEntity(PostDTO postDTO);
    List<PostDTO> findAllCommentsToCurrentPost(Long postId);
}
