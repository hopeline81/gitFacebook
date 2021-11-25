package com.example.facebookdemo.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+ multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }
}
