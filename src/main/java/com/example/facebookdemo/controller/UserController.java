package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.RegisterDTO;
//import com.example.facebookdemo.service.contrack.ProfileService;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.repository.ProfileRepository;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController extends BaseController implements WebMvcConfigurer {
    private final UserService userService;
    private final ProfileRepository profileRepository;
 //   private final ProfileService profileService;

    @Autowired
    public UserController(UserService userService, ProfileRepository profileRepository) {
        this.userService = userService;
  //      this.profileService = profileService;
        this.profileRepository = profileRepository;
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/login")
    public ModelAndView login() {
        return send("login.html");
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/login")
    public ModelAndView login(@Validated @ModelAttribute("user") RegisterDTO registerDTO
            , BindingResult result
            , RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", registerDTO);
            return redirect("register");
        }
        userService.register(registerDTO);
        return redirect("login");
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/register")
    public ModelAndView register(@Validated @ModelAttribute("user") RegisterDTO registerDTO
            , BindingResult result
            , RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", registerDTO);
            return redirect("register");
        }
        userService.register(registerDTO);
        return redirect("login");
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/register")
    public ModelAndView register() {
        return send("register");
    }

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/profile")
//    public ModelAndView profile(Principal principal) {
//        return send("profile", "username", principal.getName());
//    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ModelAndView userProfile(){
        List<Profile> profiles = profileRepository.findAll();
        return send("profile", "profiles", profiles);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/my-page")
    public ModelAndView myPage() {
        return send("profile");
    }
}