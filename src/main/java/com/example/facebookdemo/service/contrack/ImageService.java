package com.example.facebookdemo.service.contrack;

import com.example.facebookdemo.dto.ImageDTO;
import com.example.facebookdemo.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<Image> allImages();
    Image save(ImageDTO imageDto) throws IOException;
}