package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.FriendRequestDTO;
import com.example.facebookdemo.service.contrack.FriendRequestService;
import com.example.facebookdemo.service.contrack.UserService;
import com.example.facebookdemo.service.implementation.FriendRequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SendRequestController extends BaseController {

    private final UserService userService;
    private final FriendRequestService friendRequestService;

    @Autowired
    public SendRequestController(UserService userService, FriendRequestService friendRequestService) {
        this.userService = userService;
        this.friendRequestService = friendRequestService;
    }

    @GetMapping("send_friend_request")
    public ModelAndView sendFriendRequest() {
        return send("send-friend-request");
    }

    @PostMapping("send_friend_request")
    public ModelAndView sendFriendRequest(@ModelAttribute FriendRequestDTO friendRequestDTO,
                                          @Param("firstName") String firstName,
                                          @Param("lastName") String lastName) {
        friendRequestService.sendFriendRequest(friendRequestDTO, firstName, lastName);
        return redirect("profile");
    }
}
