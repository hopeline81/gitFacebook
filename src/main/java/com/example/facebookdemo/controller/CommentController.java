package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CommentController extends BaseController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/add_comments")
    public ModelAndView addComments(@RequestParam("parentPostId") String parentPostId, Model model) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(Long.valueOf(parentPostId));
        model.addAttribute("parentPostId", parentPostId);

        return send("add-comments");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add_comments")
    public ModelAndView addComments(@ModelAttribute("postDTO") PostDTO postDTO,
                                    @AuthenticationPrincipal User user) {
        Long parentPostId = postDTO.getId();
        Post comment = commentService.convertCommentDTOToEntity(postDTO);
        commentService.addComment(parentPostId, comment, user.getId());

        return redirect("/posts");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/comments")
    public ModelAndView getAllComments(@RequestParam("parentPostId") String parentPostId,
                                       Model model ) {
        List<PostDTO> comments = commentService.findAllCommentsToCurrentPost(Long.valueOf(parentPostId));
        model.addAttribute("comments", comments);

        return send("comments", "comments", comments);
    }
}
