package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.PostRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.CommentService;
import com.example.facebookdemo.service.contrack.PostService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Post save(PostDTO postDTO, User user) {
        Post post = new Post();
        post.setTextPost(postDTO.getText());
        post.setPostDate(LocalDateTime.now(ZoneOffset.UTC));
        post.setUser(user);
        post.setNumberOfLikes(0);

        return postRepository.save(post);
    }

    @Override
    public List<PostDTO> getUserAndFriendPostDTOS(User user1) {
        List<Post> allUserPosts = user1.getPosts().stream()
                .filter(post -> post.getParent() == null)
                .collect(Collectors.toList());

        List<Post> allFriendPosts = user1.getFriends().stream()
                .flatMap(friend -> friend.getPosts().stream())
                .filter(post -> post.getParent() == null)
                .collect(Collectors.toList());

        return Stream.concat(allUserPosts.stream(), allFriendPosts.stream())
                .sorted(Comparator.comparing(Post::getPostDate).reversed())
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Post convertPostDTOToEntity(PostDTO postDTO) {

        return modelMapper.map(postDTO, Post.class);
    }

    public PostDTO convertPostToPostDTO(Post postCreated) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper.map(postCreated, PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.getById(id);

        return convertPostToPostDTO(post);
    }

    @Override
    public void createLike(Post post, Long userId) {
        User user = userRepository.findById(userId).get();
        List<User> likedUsers = post.getUsersLikes();
        Integer numberOfLike = post.getNumberOfLikes();

        if(likedUsers.contains(user)){
            post.setNumberOfLikes(numberOfLike - 1);
            likedUsers.remove(user);
        }else {
            post.setNumberOfLikes(numberOfLike + 1);
            likedUsers.add(user);
        }
        postRepository.save(post);
    }
}
