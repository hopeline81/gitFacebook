package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.ImageResponseDTO;
import com.example.facebookdemo.entity.Image;
import com.example.facebookdemo.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageUploadService {

    String uploadImage(MultipartFile multipartFile) throws IOException;
    void uploadAvatar(Long profileId, MultipartFile multipartFile) throws IOException;
    void uploadUserImage(User user, MultipartFile multipartFile, String imageText) throws IOException;
    Image getImageById(Long valueOf);
    List<Image> getAllImages();
    ImageResponseDTO convertImageToImageDTOResponse (Image image);
    List<ImageResponseDTO> convertImagesToImageDTOs(List<Image> images);
    void updateLikes(Image image, Long userId);
}
