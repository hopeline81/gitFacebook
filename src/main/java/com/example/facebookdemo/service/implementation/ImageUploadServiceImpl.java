package com.example.facebookdemo.service.implementation;

import com.example.facebookdemo.dto.ImageResponseDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.User;
import com.example.facebookdemo.repository.ImageRepository;
import com.example.facebookdemo.repository.UserRepository;
import com.example.facebookdemo.service.contrack.ImageUploadService;
import com.example.facebookdemo.service.implementation.util.FileUtil;
import com.example.facebookdemo.service.implementation.util.FirebaseStorageCreateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Transactional
@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    private final ImageRepository imageRepository;
    private final ProfileServiceImpl profileService;
    private final UserRepository userRepository;

    @Autowired
    public ImageUploadServiceImpl(ImageRepository imageRepository, ProfileServiceImpl profileService, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.profileService = profileService;
        this.userRepository = userRepository;
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        File file = FileUtil.multipartToFile(multipartFile);
        String objectName = generateFileName(multipartFile);
        FirebaseStorageCreateUtil.firebaseStorageCreateUtil(file, objectName);

        return objectName;
    }

    @Override
    public void uploadAvatar(Long profileId, MultipartFile multipartFile) throws IOException {
        String avatarUrl = uploadImage(multipartFile);

        profileService.updateAvatar(profileId, avatarUrl);
    }

    @Override
    public void uploadUserImage(User user, MultipartFile multipartFile, String imageText) throws IOException {
        String imageUrl = uploadImage(multipartFile);
        Image image = new Image();
        image.setUser(user);
        image.setImageUrl(imageUrl);
        image.setDescription(imageText);
        image.setImageUploadDate(LocalDateTime.now());
        image.setNumberOfLikesImage(0);
        imageRepository.save(image);
    }

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.getById(imageId);
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public void updateLikes(Image image, Long userId) {
        User user = userRepository.findById(userId).get();
        List<User> usersWhoLikedImage = image.getUsersLikes();
        Integer numberOfLikesImage = image.getNumberOfLikesImage();
        if(usersWhoLikedImage.contains(user)){
            image.setNumberOfLikesImage(numberOfLikesImage - 1);
            usersWhoLikedImage.remove(user);
        }else{
            image.setNumberOfLikesImage(numberOfLikesImage + 1);
            usersWhoLikedImage.add(user);
        }
        imageRepository.save(image);
    }

    public List<ImageResponseDTO> convertImagesToImageDTOs(List<Image> images) {
        List<ImageResponseDTO> responseImages = new ArrayList<>();
        images.forEach(image -> {
            ImageResponseDTO imageResponseDTO = new ImageResponseDTO();
            imageResponseDTO.setId(image.getId());
            imageResponseDTO.setUrl(image.getImageUrl());
            imageResponseDTO.setDescription(image.getDescription());
            imageResponseDTO.setUserId(image.getUser().getId());
            imageResponseDTO.setNumberOfLikesImage(image.getNumberOfLikesImage());
            imageResponseDTO.setImageUploadDate(image.getImageUploadDate());
            imageResponseDTO.setUsersLikedImage(image.getUsersLikes());
            responseImages.add(imageResponseDTO);
        });
        return responseImages;
    }

    public ImageResponseDTO convertImageToImageDTOResponse (Image image) {
        ImageResponseDTO imageResponseDTO = new ImageResponseDTO();
        imageResponseDTO.setDescription(image.getDescription());
        imageResponseDTO.setUrl(image.getImageUrl());
        imageResponseDTO.setNumberOfLikesImage(image.getNumberOfLikesImage());
        imageResponseDTO.setImageUploadDate(image.getImageUploadDate());

        return imageResponseDTO;
    }

    private String generateFileName(MultipartFile multiPart) {

        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename())
                         .replace(" ", "_");
    }
}
