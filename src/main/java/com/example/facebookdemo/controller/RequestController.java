package com.example.facebookdemo.controller;

import com.example.facebookdemo.entity.FriendRequest;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RequestController extends BaseController {

    private final FriendRequestService friendRequestService;

    @Autowired
    public RequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @GetMapping("send_friend_request")
    public ModelAndView sendFriendRequest() {
        return send("send-friend-request");
    }

    @PostMapping("send_friend_request")
    public ModelAndView sendFriendRequest(@AuthenticationPrincipal User user,
                                          @RequestParam("firstName") String firstName,
                                          @RequestParam("lastName") String lastName) {

        FriendRequest friendRequest = friendRequestService.sendFriendRequest(user, firstName, lastName);
        Set<FriendRequest> friendRequests = new HashSet<>();
        friendRequests.add(friendRequest);

        user.setUserRequests(friendRequests);
        return redirect("profile");
    }

    @GetMapping("requests")
    public ModelAndView allRequest(@AuthenticationPrincipal User user) {
        Set<FriendRequest> object = friendRequestService.findRequestToUser(user);
        return send("requests", "requests", object);
    }

    @GetMapping("accept_friend_request")
    public ModelAndView acceptRequest(@AuthenticationPrincipal User user,
                                      @RequestParam("requesterId") String requesterId) {

        Long requesterUserId = Long.valueOf(requesterId);
        User newFriend = friendRequestService.findRequesterUser(requesterUserId);

        friendRequestService.addNewFriend(user, newFriend);
        friendRequestService.changeRequestStatusFromPendingToAccept(user, newFriend);
        return redirect("profile");
    }
}
