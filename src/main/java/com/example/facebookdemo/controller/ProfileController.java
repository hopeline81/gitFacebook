package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.ProfileDTO;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController extends BaseController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ModelAndView userProfile(@AuthenticationPrincipal User user) {
        ProfileDTO profileDTO = profileService.createNewProfileDTO(user.getEmail());

        return send("profile", "profileDTO", profileDTO);
    }
}
