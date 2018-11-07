package com.wiki.controller;

import com.wiki.model.dto.UserDTO;
import com.wiki.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
    @GetMapping("/findByUsername")
    public ResponseEntity<UserDTO> getByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }
}
