package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.PostService;
import com.example.facebookdemo.service.contrack.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class PostController extends BaseController {

    private final PostService postService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public PostController(PostService postService, ModelMapper modelMapper, UserService userService) {
        this.postService = postService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/add")
    public ModelAndView createdPost() {
        return send("new-post");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/add")
    public ModelAndView createdPost(@Validated @ModelAttribute PostDTO postDTO,
                                    BindingResult result, @AuthenticationPrincipal User user) {
        if (result.hasErrors()) {
            return send("new-post");
        }
        postService.save(postDTO, user);
        return redirect("/posts");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts")
    public String allPosts(@AuthenticationPrincipal User user, Model model) {
        List<PostDTO> posts = postService.allPosts().stream()
                .sorted(Comparator.comparing(Post::getPostDate).reversed())
                .map(post -> modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/likes")
    public ModelAndView addLike(@RequestParam("postId") String postId,
                                @AuthenticationPrincipal User user,
                                Model model) {
        try {
            PostDTO postDTO = postService.getPostById(Long.valueOf(postId));
            Post post = postService.convertToEntity(postDTO);
            postService.createLike(post, user.getId());
        } catch (Exception e) {
            model.addAttribute("message", "This post does not exist");
            return send("message");
        }
        return redirect("/posts");
    }
}
