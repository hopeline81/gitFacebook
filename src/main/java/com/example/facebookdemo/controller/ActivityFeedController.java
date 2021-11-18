package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.ActivityFeedDTO;
import com.example.facebookdemo.dto.ProfileDTO;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.implementation.ActivityFeedServiceIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ActivityFeedController extends BaseController {

    private final ActivityFeedServiceIml activityFeedService;

    @Autowired
    public ActivityFeedController(ActivityFeedServiceIml activityFeedService) {
        this.activityFeedService = activityFeedService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/activity_feed")
    public ModelAndView activityFeed(@AuthenticationPrincipal User user, Model model) {

        ActivityFeedDTO activityFeedDTO = activityFeedService.showActivityFeedDTO(user);
        model.addAttribute(activityFeedDTO);

        return send("activity-feed" , "activityFeedDTO",  activityFeedDTO);
    }
}
