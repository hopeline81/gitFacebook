package com.example.facebookdemo.service.implementation.util;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FirebaseStorageCreateUtil {

    private static String BUCKET_NAME = "facebook-nadezhda.appspot.com";

    public static void  firebaseStorageCreateUtil(File file, String objectName) throws IOException {
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        Credentials credentials = GoogleCredentials.fromStream(new FileInputStream("ServiceAccountKey.json"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
    }
}
