package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.FriendRequestDTO;
import com.example.facebookdemo.entity.FriendRequest;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SendRequestController extends BaseController {

    private final FriendRequestService friendRequestService;

    @Autowired
    public SendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @GetMapping("send_friend_request")
    public ModelAndView sendFriendRequest() {
        return send("send-friend-request");
    }

    @PostMapping("send_friend_request")
    public ModelAndView sendFriendRequest(@AuthenticationPrincipal User user,
                                          @Param("firstName") String firstName,
                                          @Param("lastName") String lastName) {

        FriendRequest friendRequest = friendRequestService.sendFriendRequest(user, firstName, lastName);
        Set<FriendRequest> friendRequests = new HashSet<>();
        friendRequests.add(friendRequest);

        user.setUserRequests(friendRequests);
        return redirect("profile");
    }

    @PostMapping("all_request")
    public ModelAndView allRequest(@AuthenticationPrincipal User user) {
        Set<FriendRequest> object = friendRequestService.findRequestToUser(user);
        return send("all_request", "allRequests", object);
    }
}
