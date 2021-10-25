package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.ImageDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.firebase.FirebaseStrategy;
import com.example.facebookdemo.repository.ImageRepository;
import com.example.facebookdemo.service.contrack.ImageService;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

@Service
public class ImageUploadService implements ImageService {

    private FirebaseStrategy firebaseStrategy;
    String bucketName = "images";
    private final ImageRepository imageRepository;
    private final ProfileServiceImpl profileService;

    @Autowired
    public ImageUploadService(FirebaseStrategy firebaseStrategy, ImageRepository imageRepository, ProfileServiceImpl profileService) {
        this.firebaseStrategy = firebaseStrategy;
        this.imageRepository = imageRepository;
        this.profileService = profileService;
    }

    @Override
    public Image save(ImageDTO imageDto) throws IOException {
        Image image = new Image();
        image.setImageUrl(uploadImage(imageDto.getImage()));
        return image;
    }

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        String objectName = generateFileName(multipartFile);
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("ServiceAccountKey.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        return objectName;
    }

    public String uploadAvatar(Long profileId,MultipartFile multipartFile) throws IOException{
        String avatarUrl = uploadImage(multipartFile);
        Image image = new Image();
        image.setImageUrl(avatarUrl);
        Image storedImage = imageRepository.save(image);
        profileService.updateAvatar(profileId, storedImage.getImageUrl());

        return avatarUrl;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename()).replace(" ", "_");
    }

    @Override
    public List<Image> allImages() {
        return null;
    }


}
