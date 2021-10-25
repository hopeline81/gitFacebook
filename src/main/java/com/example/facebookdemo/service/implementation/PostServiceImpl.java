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
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post save(PostDTO postDTO, User user, Image image) {
        Post post = new Post();
        post.setTextPost(postDTO.getText());
        post.setPostDate(LocalDateTime.now(ZoneOffset.UTC));
        post.setUser(user);

//        post.setImages(image);

        return postRepository.save(post);
    }

    @Override
    public List<Post> getPosts() {return postRepository.findAll();}

}
