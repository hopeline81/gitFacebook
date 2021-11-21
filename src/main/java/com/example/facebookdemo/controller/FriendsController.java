package com.example.facebookdemo.controller;

import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.FriendService;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class FriendsController extends BaseController {

    private final UserService userService;
    private final FriendService friendService;

    @Autowired
    public FriendsController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/friends")
    public ModelAndView allFriends(@AuthenticationPrincipal User user){
        User user1 = userService.loadUserByUsername(user.getEmail());
        Set<User> friends = friendService.getFriends(user1);

        return send("friends", "friends", friends);
    }
}
