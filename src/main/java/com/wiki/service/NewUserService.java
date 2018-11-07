package com.wiki.service;

import com.wiki.Utils;
import com.wiki.model.Identity;
import com.wiki.model.User;
import com.wiki.model.dto.NewUserDTO;
import com.wiki.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class NewUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public NewUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(NewUserDTO user) {
        userRepository.save(new User(user.getName(), user.getSurname(),
                new Identity(user.getEmail(), passwordEncoder.encode(user.getPassword()), Utils.setOf("ROLE_USER"))));
    }
}
