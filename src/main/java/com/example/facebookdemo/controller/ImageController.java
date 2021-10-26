package com.example.facebookdemo.controller;

import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.implementation.ImageUploadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Controller
public class ImageController extends BaseController {

    private final ImageUploadServiceImpl imageUploadServiceImpl;

    @Autowired
    public ImageController(ImageUploadServiceImpl imageUploadServiceImpl) {
        this.imageUploadServiceImpl = imageUploadServiceImpl;
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
    public Object avatarUpload(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        Long profileId = user.getProfile().getId();
        return imageUploadServiceImpl.uploadAvatar(profileId, multipartFile);
    }
}
