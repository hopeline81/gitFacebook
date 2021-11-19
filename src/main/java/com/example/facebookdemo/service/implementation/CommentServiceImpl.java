package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.PostRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.CommentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(PostRepository postRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Post convertCommentDTOToEntity(PostDTO postDTO) {
        Post comment = new Post();
        comment.setTextPost(postDTO.getText());

        return comment;
    }

    public void addComment(Long parentPostId, Post comment, Long userId) {
        User user = userRepository.findUserById(userId).get();
        Post post1 = postRepository.findById(parentPostId).get();
        comment.setUser(user);
        comment.setParent(post1);
        comment.setNumberOfLikes(0);
        comment.setPostDate(LocalDateTime.now());
        comment = postRepository.save(comment);
        List<Post> comments = post1.getComments();
        comments.add(comment);
        
        postRepository.save(post1);
    }

    public List<PostDTO> findAllCommentsToCurrentPost(Long postId) {
        Post currentPost = postRepository.findFirstById(postId).get();

        return currentPost.getComments().stream()
                .map(this::convertPostToPostDTO)
                .collect(Collectors.toList());
    }

    public PostDTO convertPostToPostDTO(Post postCreated) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper.map(postCreated, PostDTO.class);
    }
}
