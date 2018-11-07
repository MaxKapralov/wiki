package com.wiki.controller;

import com.wiki.model.dto.NewUserDTO;
import com.wiki.service.NewUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-up")
public class NewUserController {

    private final NewUserService newUserService;

    public NewUserController(NewUserService newUserService) {
        this.newUserService = newUserService;
    }

    @PostMapping
    public ResponseEntity<Void> saveNewUser(@RequestBody NewUserDTO user) {
        newUserService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
