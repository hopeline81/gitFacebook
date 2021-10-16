package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.ProfileDTO;
import com.example.facebookdemo.dto.UserDTO;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.ChangeProfileService;
import com.example.facebookdemo.service.contrack.ProfileService;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class ChangeProfileController extends BaseController{

    private ChangeProfileService changeProfileService;
    private ProfileService profileService;


    public ChangeProfileController( ChangeProfileService changeProfileService, ProfileService profileService) {
        this.changeProfileService = changeProfileService;
        this.profileService = profileService;
    }

        @GetMapping("/profile/update")
    public ModelAndView viewDetails(@AuthenticationPrincipal User user, Model model) {
        return send("profile-update");
    }

    @PostMapping("/profile/update")
    public ModelAndView saveDetails(@AuthenticationPrincipal User user,
                                    @ModelAttribute("profile") UserDTO userDTO,
                                    RedirectAttributes redirectAttributes) throws IOException{
//        if(!multipartFile.isEmpty()){
//            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
//            profileDTO.setPhoto(fileName);
//            Profile savedProfile = profileService.updateProfile(profileDTO);
//
//            String uploadDir = "profile-photos/" + savedProfile.getId();
//
//           Utility.saveFile(uploadDir, fileName, multipartFile  profileService.updateProfile(profile);
//        }
//        profileService.updateProfile(user.getProfile());
        user.setProfile(changeProfileService.updateProfileDetails(user,user.getProfile(), userDTO));
        redirectAttributes.addFlashAttribute("message", "Your profile details is updated.");
        return send("profile");
    }

}
