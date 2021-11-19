package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.ActivityFeedDTO;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.ActivityFeedService;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ActivityFeedController extends BaseController {

    private final ActivityFeedService activityFeedService;
    private final UserService userService;

    public ActivityFeedController(ActivityFeedService activityFeedService, UserService userService) {
        this.activityFeedService = activityFeedService;
        this.userService = userService;
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/activity_feed")
    public ModelAndView activityFeed(@AuthenticationPrincipal User user, Model model) {

        User user1 = userService.loadUserByUsername(user.getEmail());
        ActivityFeedDTO activityFeedDTO = activityFeedService.showActivityFeedDTO(user1);
        model.addAttribute(activityFeedDTO);

        return send("activity-feed" , "activityFeedDTO",  activityFeedDTO);
    }
}
