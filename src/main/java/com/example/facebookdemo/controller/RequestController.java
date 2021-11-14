package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.FriendRequestDTO;
import com.example.facebookdemo.entity.FriendRequest;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.FriendRequestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class RequestController extends BaseController {

    private final FriendRequestService friendRequestService;
    private final ModelMapper modelMapper;

    @Autowired
    public RequestController(FriendRequestService friendRequestService, ModelMapper modelMapper) {
        this.friendRequestService = friendRequestService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("send_friend_request")
    public ModelAndView sendFriendRequest(@AuthenticationPrincipal User user,
                                          @ModelAttribute FriendRequestDTO friendRequestDTO,
                                          @RequestParam("requesterId") String requestedId,
                                          Model model) {

        try{
            FriendRequest friendRequest = friendRequestService.sendFriendRequest(user, requestedId);
            Set<FriendRequest> friendRequests = new HashSet<>();
            friendRequests.add(friendRequest);
            user.setUserRequests(friendRequests);
        }catch (Exception e){
            model.addAttribute("message", "This request already exist");
            return send ("message");
        }
        return redirect("/profile");
    }

    @GetMapping("requests")
    public ModelAndView allRequest(@AuthenticationPrincipal User user) {
        List<FriendRequestDTO> object = friendRequestService.findRequestToUser(user);
        return send("requests", "requests", object);
    }

    @GetMapping("accept_friend_request")
    public ModelAndView acceptRequest(@AuthenticationPrincipal User user,
                                      @RequestParam("requesterId") String requesterId,
                                      Model model) {

        Long requesterUserId = Long.valueOf(requesterId);
        User newFriend = friendRequestService.findRequesterUser(requesterUserId);

        try {
            friendRequestService.addNewFriend(user, newFriend);
            friendRequestService.changeRequestStatusFromPendingToAccept(user, newFriend);
        }catch (Exception e) {
            model.addAttribute("message", "This request already accepted");
            return send ("message");
        }
        return redirect("/profile");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/friends")
    public ModelAndView allFriends(@AuthenticationPrincipal User user){
        Set<User> friends = friendRequestService.getFriends(user);
        return send("friends", "friends", friends);
    }
}
