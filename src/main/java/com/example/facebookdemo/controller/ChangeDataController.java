package com.example.facebookdemo.controller;

import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.ProfileService;
import com.example.facebookdemo.service.implementation.Utility;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Objects;

@Controller
public class ChangeDataController extends BaseController{

    private ProfileService profileService;

    public ChangeDataController(ProfileService profileService) {
        this.profileService = profileService;
    }

        @GetMapping("/profile/update")
    public ModelAndView viewDetails(@AuthenticationPrincipal User user, Model model) {
        return send("profile-update");
    }

    @PostMapping("/profile/update")
    public ModelAndView saveDetails(@AuthenticationPrincipal User user,
                                    RedirectAttributes redirectAttributes,
                                    Profile profile,
                                    @RequestParam("image")MultipartFile multipartFile) throws IOException{
//        if(!multipartFile.isEmpty()){
//            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
//            profile.setPhoto(fileName);
//            Profile savedProfile = profileService.updateProfile(profile);
//
//            String uploadDir = "profile-photos/" + savedProfile.getId();
//
//            Utility.saveFile(uploadDir, fileName, multipartFile);
//        }else{
//            if(profile.getPhoto().isEmpty()) profile.setPhoto(null);
//            profileService.updateProfile(profile);
//        }
        profileService.updateProfile(user.getProfile());
        redirectAttributes.addFlashAttribute("message", "Your profile details is updated.");
        return send("profile");
    }

}
