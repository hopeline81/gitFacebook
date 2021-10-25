package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.ImageDTO;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.implementation.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Controller
public class ImageController extends BaseController {

    private final ImageUploadService imageUploadService;

    @Autowired
    public ImageController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @GetMapping("/image/upload")
    public ModelAndView imageUpload() {
        return send("upload");
    }

    @PostMapping("/image/upload")
    public ModelAndView imageUpload(@ModelAttribute ImageDTO imageDTO) throws IOException {
      //  imageUploadService.uploadImage();
        return redirect("/");
    }

    @PostMapping("/avatar/upload")
    public Object avatarUpload(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        Long profileId = user.getProfile().getId();
        return imageUploadService.uploadAvatar(profileId, multipartFile);
    }

    @PostMapping("/profile/pic")
    public Object upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return imageUploadService.uploadImage(multipartFile);
    }

//    @PostMapping("/profile/pic/{fileName}")
//    public Object download(@PathVariable String fileName, HttpServletRequest request) throws Exception {
//        return imageUploadService.downloadFile(fileName, request);
//    }
}
