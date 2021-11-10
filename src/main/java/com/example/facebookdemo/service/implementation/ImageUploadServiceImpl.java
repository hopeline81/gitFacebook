package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.ImageDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ImageRepository;
import com.example.facebookdemo.service.contrack.ImageUploadService;
import com.example.facebookdemo.service.implementation.util.FileUtil;
import com.example.facebookdemo.service.implementation.util.FirebaseStorageCreateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    private final ImageRepository imageRepository;
    private final ProfileServiceImpl profileService;
    private final ModelMapper modelMapper;

    @Autowired
    public ImageUploadServiceImpl(ImageRepository imageRepository, ProfileServiceImpl profileService, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.profileService = profileService;
        this.modelMapper = modelMapper;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File file = FileUtil.multipartToFile(multipartFile);
        String objectName = generateFileName(multipartFile);
        FirebaseStorageCreateUtil.firebaseStorageCreateUtil(file, objectName);
        return objectName;
    }

    @Override
    public Profile uploadAvatar(Long profileId, MultipartFile multipartFile) throws IOException {
        String avatarUrl = uploadImage(multipartFile);
        return profileService.updateAvatar(profileId, avatarUrl);
    }

    @Override
    public Image uploadUserImage(User user, MultipartFile multipartFile, String imageText) throws IOException {
        String imageUrl = uploadImage(multipartFile);
        Image image = new Image();
        image.setUser(user);
        image.setImageUrl(imageUrl);
        image.setDescription(imageText);
        return imageRepository.save(image);
    }

    @Override
    public List<Image> getImages(User user) {
        return imageRepository.findAllByUser(user);
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }
}
