package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.ImageDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Post;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ImageUploadService {
    String uploadImage(MultipartFile multipartFile) throws IOException;
    Profile uploadAvatar(Long profileId, MultipartFile multipartFile) throws IOException;
    List<Image> uploadUserImage(User user, MultipartFile multipartFile, String imageText) throws IOException;
    List<Image> getImages(User user);
}
