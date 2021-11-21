package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.FriendRequestDTO;
import com.example.facebookdemo.entity.FriendRequest;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.FriendRequestService;
import com.example.facebookdemo.service.contrack.FriendService;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class RequestController extends BaseController {

    private final FriendRequestService friendRequestService;
    private final FriendService friendService;
    private final UserService userService;

    @Autowired
    public RequestController(FriendRequestService friendRequestService, FriendService friendService, UserService userService) {
        this.friendRequestService = friendRequestService;
        this.friendService = friendService;
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("send_friend_request")
    public ModelAndView sendFriendRequest(@AuthenticationPrincipal User user,
                                          @RequestParam("requesterId") String requestedId,
                                          Model model) {
        try{
            User user1 = userService.loadUserByUsername(user.getEmail());
            FriendRequest friendRequest = friendRequestService.sendFriendRequest(user1, requestedId);
            Set<FriendRequest> friendRequests = new HashSet<>();
            friendRequests.add(friendRequest);
            user1.setUserRequests(friendRequests);
        }catch (Exception e){
            model.addAttribute("message", "This request already exist");
            return send ("message");
        }
        return redirect("/profile");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("requests")
    public ModelAndView allRequest(@AuthenticationPrincipal User user) {

        User user1 = userService.loadUserByUsername(user.getEmail());
        List<FriendRequestDTO> requests = friendRequestService.findRequestToUser(user1);

        return send("requests", "requests", requests);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("accept_friend_request")
    public ModelAndView acceptRequest(@AuthenticationPrincipal User user,
                                      @RequestParam("requesterId") String requesterId,
                                      Model model) {

        Long requesterUserId = Long.valueOf(requesterId);
        User user1 = userService.loadUserByUsername(user.getEmail());
        User newFriend = friendRequestService.findRequesterUserById(requesterUserId);
        try {
            friendService.addNewFriend(user1, newFriend.getId());
            friendRequestService.changeRequestStatusFromPendingToAccept(user1, newFriend);
        }catch (Exception e) {
            model.addAttribute("message", "This request already accepted");
            return send ("message");
        }
        return redirect("/profile");
    }
}
