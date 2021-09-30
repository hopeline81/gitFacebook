package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.dto.RegisterDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

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
    public ModelAndView userProfile(){
        List<Profile> profiles = profileRepository.findAll();
        return send("profile", "profiles", profiles);
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/profile")
    public ModelAndView profile(@Validated @ModelAttribute("user") RegisterDTO registerDTO,
                              PostDTO postDTO
            , BindingResult result
            , RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", registerDTO);
            return redirect("profile");
        }
        userService.register(registerDTO, postDTO);
        return redirect("post");
    }
}
