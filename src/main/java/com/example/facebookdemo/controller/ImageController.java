package com.example.facebookdemo.controller;

import com.example.facebookdemo.dto.ImageDTO;
import com.example.facebookdemo.dto.ImageResponseDTO;
import com.example.facebookdemo.dto.ProfileDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.service.contrack.ImageUploadService;
import com.example.facebookdemo.service.contrack.ProfileService;
import com.example.facebookdemo.service.contrack.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class ImageController extends BaseController {

    private final ImageUploadService imageUploadService;
    private final ProfileService profileService;
    private final UserService userService;

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
                                    @RequestParam("file") MultipartFile multipartFile,
                                    Model model) {
        try {
            imageUploadService.uploadUserImage(user, multipartFile, imageDTO.getDescription());
        } catch (Exception e) {
            model.addAttribute("message", "There is a problem with upload, image too large");
            return send("message");
        }

        return redirect("/images");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/images")
    public ModelAndView allUserAndFriendImages(@AuthenticationPrincipal User user, Model model) {
        User user1 = userService.loadUserByUsername(user.getEmail());
        List<ImageResponseDTO> images = imageUploadService.getUserAndFriendsImages(user1);

        return send("images", "images", images);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/avatar_upload")
    public ModelAndView avatarUpload(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        Long profileId = user.getProfile().getId();
        imageUploadService.uploadAvatar(profileId, multipartFile);
        ProfileDTO profileDTO = profileService.createNewProfileDTO(user.getEmail());

        return send("profile", "profileDTO", profileDTO);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/likesImage")
    public ModelAndView addLike(@RequestParam("imageId") String imageId,
                                @AuthenticationPrincipal User user,
                                Model model) {
        try {
            Image image = imageUploadService.getImageById(Long.valueOf(imageId));
            imageUploadService.updateLikes(image, user.getId());
        } catch (Exception e) {
            model.addAttribute("message", "This image does not exist");
            return send("message");
        }
        return redirect("/images");
    }
}
