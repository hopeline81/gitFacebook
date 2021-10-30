package com.example.facebookdemo.service.implementation;
import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.PostRepository;
import com.example.facebookdemo.service.contrack.PostService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post save(PostDTO postDTO, User user) {
        Post post = new Post();
        post.setTextPost(postDTO.getText());
        post.setPostDate(LocalDateTime.now(ZoneOffset.UTC));
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public List<Post> getPosts(User user) {
        Optional<Post> posts = postRepository.findAllByUser(user);
        return posts.stream()
                .collect(Collectors.toList());
    }
}
