package com.wiki.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private static final String PATH = "images/avatars/";
    public ClassPathResource getImage(String username) {
        ClassPathResource resource =  new ClassPathResource(PATH + username + ".jpg");
        if(!resource.exists()) {
            resource = new ClassPathResource(PATH + "default.jpg");
        }
        return resource;
    }
}
