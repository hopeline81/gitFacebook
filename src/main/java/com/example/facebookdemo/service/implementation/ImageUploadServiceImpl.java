package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ImageRepository;
import com.example.facebookdemo.service.contrack.ImageUploadService;
import com.example.facebookdemo.service.contrack.UserService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    String BUCKET_NAME = "facebook-nadezhda.appspot.com";
    private final ImageRepository imageRepository;
    private final ProfileServiceImpl profileService;
    private final UserService userService;

    @Autowired
    public ImageUploadServiceImpl(ImageRepository imageRepository, ProfileServiceImpl profileService, UserService userService) {
        this.imageRepository = imageRepository;
        this.profileService = profileService;
        this.userService = userService;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File file = FileUtil.multipartToFile(multipartFile);
        String objectName = generateFileName(multipartFile);
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("ServiceAccountKey.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

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
