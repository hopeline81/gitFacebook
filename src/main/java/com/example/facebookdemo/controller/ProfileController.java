package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController extends BaseController {

    private ProfileRepository profileRepository;
    private UserServiceImpl userService;

    @Autowired
    public ProfileController(ProfileRepository profileRepository, UserServiceImpl userService) {
        this.profileRepository = profileRepository;
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ModelAndView userProfile(@AuthenticationPrincipal User user){
       Profile profiles = profileRepository.findFirstByEmail(user.getEmail());
        return send("profile", "profiles", profiles);
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/profile")
    public ModelAndView profile(@Validated @ModelAttribute("user") UserDTO userDTO
            , BindingResult result
            , RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userDTO);
            return redirect("profile");
        }
        userService.register(userDTO);
        return redirect("post");
    }

}
