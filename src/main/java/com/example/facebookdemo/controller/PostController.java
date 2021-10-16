package com.example.facebookdemo.controller;
import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class PostController extends BaseController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/add")
    public ModelAndView createdPost(){
        return send("new-post");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/post/add")
    public ModelAndView createdPost(@Validated @ModelAttribute PostDTO postDTO,
                                    BindingResult result, @AuthenticationPrincipal User user, Image image){
        if(result.hasErrors()){
            return send("new-post");
        }
        postService.save(postDTO, user, image);
        return redirect("/posts");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts")
    public ModelAndView allPosts(){
        List<Post> object = postService.getPosts();
        return send("posts", "posts", object);
    }
}
