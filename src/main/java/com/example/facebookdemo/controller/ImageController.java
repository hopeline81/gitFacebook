package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.ImageDTO;
import com.example.facebookdemo.dto.PostDTO;
import com.example.facebookdemo.dto.ProfileDTO;
import com.example.facebookdemo.dto.ResponseImageDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.ImageUploadService;
import com.example.facebookdemo.service.contrack.ProfileService;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ImageController extends BaseController {

    private ImageUploadService imageUploadService;
    private ProfileService profileService;
    private UserService userService;

    @Autowired
    public ImageController(ImageUploadService imageUploadService, ProfileService profileService, UserService userService) {
        this.imageUploadService = imageUploadService;
        this.profileService = profileService;
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/upload_image")
    public ModelAndView imageUpload() {
        return send("new-image");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/upload_image")
    public ModelAndView imageUpload(@AuthenticationPrincipal User user,
                                    @ModelAttribute ImageDTO imageDTO,
                                    BindingResult result,
                                    @RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (result.hasErrors()) {
            return send("new-image");
        }
        imageUploadService.uploadUserImage(user, multipartFile, imageDTO.getDescription());
        return redirect("/images");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/images")
    public ModelAndView allImages() {
        List<Image> images = imageUploadService.getAllImages();
        List<ResponseImageDTO> responseImages = new ArrayList<>();
        images.forEach(image -> {
            ResponseImageDTO responseImageDTO = new ResponseImageDTO();
            responseImageDTO.setId(image.getId());
            responseImageDTO.setUrl(image.getImageUrl());
            responseImageDTO.setDescription(image.getDescription());
            responseImageDTO.setUserId(image.getUser().getId());
            responseImageDTO.setNumberOfLikesImage(image.getNumberOfLikesImage());
            responseImageDTO.setUsersLikedImage(image.getUsersLikes());
            responseImages.add(responseImageDTO);
        });
//        List<Image> friendsImages = user.getFriends().stream()
//            .flatMap(friend -> friend.getImages().stream())
//            .collect(Collectors.toList());

        return send("images", "images", responseImages);
    }

    @PostMapping("/avatar_upload")
    public ModelAndView avatarUpload(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        Long profileId = user.getProfile().getId();
        imageUploadService.uploadAvatar(profileId, multipartFile);
        ProfileDTO profileDTO = profileService.createNewProfileDTO(user.getEmail());

        return send("profile", "profileDTO", profileDTO);
    }

    @GetMapping("/likesImage")
    public ModelAndView addLike(@RequestParam("imageId") String imageId,
                                @AuthenticationPrincipal User user,
                                Model model) {
        try {
            Image image = imageUploadService.getImageById(Long.valueOf(imageId));
            imageUploadService.update(image, user.getId());
        } catch (Exception e) {
            model.addAttribute("message", "This image does not exist");
            return send("message");
        }
        return redirect("/images");
    }
}
