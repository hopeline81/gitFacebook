//package com.example.facebookdemo.controller;
//
//import com.example.facebookdemo.dto.RegisterDTO;
//import com.example.facebookdemo.entity.Profile;
//import com.example.facebookdemo.repository.ProfileRepository;
//import com.example.facebookdemo.service.contrack.ProfileService;
//import com.example.facebookdemo.service.contrack.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.security.Principal;
//import java.util.List;
//
//@Controller
//public class ProfileController extends BaseController {
//
//    private ProfileService profileService;
//    private ProfileRepository profileRepository;
//
//    @Autowired
//    public ProfileController(ProfileService  profileService, ProfileRepository profileRepository) {
//        this. profileService = profileService;
//        this.profileRepository = profileRepository;
//    }
//
//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/profile")
//    public ModelAndView userProfile(){
//        List<Profile> profiles = profileRepository.findAll();
//        return send("profile", "profiles", profiles);
//    }
//}
