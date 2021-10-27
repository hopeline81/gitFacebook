package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.Profile;
import com.example.facebookdemo.repository.ImageRepository;
import com.example.facebookdemo.service.contrack.ImageService;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ImageUploadServiceImpl implements ImageService {

    String BUCKET_NAME = "facebook-nadezhda.appspot.com";
    private final ImageRepository imageRepository;
    private final ProfileServiceImpl profileService;

    @Autowired
    public ImageUploadServiceImpl(ImageRepository imageRepository, ProfileServiceImpl profileService) {
        this.imageRepository = imageRepository;
        this.profileService = profileService;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File file = convertMultiPartToFile(multipartFile);
        String objectName = generateFileName(multipartFile);
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("ServiceAccountKey.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        return objectName;
    }

    @Override
    public Profile uploadAvatar(Long profileId, MultipartFile multipartFile) throws IOException{
        String avatarUrl = uploadImage(multipartFile);
        Image image = new Image();
        image.setImageUrl(avatarUrl);
//TODO check saving
        Image storedImage = imageRepository.save(image);
        return profileService.updateAvatar(profileId, storedImage);
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
