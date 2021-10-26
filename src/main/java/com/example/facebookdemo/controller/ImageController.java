package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.ProfileDTO;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.ProfileService;
import com.example.facebookdemo.service.implementation.ImageUploadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


@Controller
public class ImageController extends BaseController {

    private final ImageUploadServiceImpl imageUploadServiceImpl;
    private ProfileService profileService;

    @Autowired
    public ImageController(ImageUploadServiceImpl imageUploadServiceImpl, ProfileService profileService) {
        this.imageUploadServiceImpl = imageUploadServiceImpl;
        this.profileService = profileService;
    }

//    @GetMapping("/image/upload")
//    public ModelAndView imageUpload() {
//        return send("upload");
//    }
//
//    @PostMapping("/image/upload")
//    public ModelAndView imageUpload(@ModelAttribute ImageDTO imageDTO) throws IOException {
//        imageUploadService.uploadImage();
//        return redirect("/");
//    }

    @PostMapping("/avatar_upload")
    public ModelAndView avatarUpload(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        Long profileId = user.getProfile().getId();
        Profile profile = imageUploadServiceImpl.uploadAvatar(profileId, multipartFile);
        ProfileDTO profileDTO = profileService.createNewProfileDTO(user.getEmail());

        return send("profile", "profileDTO", profileDTO);
    }
}
