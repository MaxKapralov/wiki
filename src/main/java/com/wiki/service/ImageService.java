package com.wiki.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    private static final String PATH = "\\wiki\\images\\avatars\\";
    private static final String DIR = "user.home";

    public ByteArrayResource getImage(String username) throws IOException {
        Path path = Paths.get(System.getProperty(DIR) + PATH + username + ".jpg");
        ByteArrayResource resource;
        if(Files.exists(path)) {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } else {
            resource = new ByteArrayResource(Files.readAllBytes(Paths.get(System.getProperty(DIR) + PATH + "default.jpg")));
        }
        return resource;
    }
    public void saveImage(MultipartFile image) throws IOException {
        Path path = Paths.get(System.getProperty(DIR) + PATH + image.getOriginalFilename() + ".jpg");
        if(Files.notExists(path)) {
            Files.createFile(path);
        }
        Files.write(path, image.getBytes());
    }
}
