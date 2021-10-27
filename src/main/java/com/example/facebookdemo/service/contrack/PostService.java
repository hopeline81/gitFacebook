package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import java.util.List;

public interface PostService {

    Post save(PostDTO postDTO, User user, Image image);
    List<Post> getPosts(User user);
}
