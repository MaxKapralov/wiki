package com.wiki.controller;

import com.wiki.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping(value = "/avatar/{username}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getAvatar(@PathVariable("username") String username, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(imageService.getImage(username).getInputStream(), response.getOutputStream());
    }
}
