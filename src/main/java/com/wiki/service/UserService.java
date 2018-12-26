package com.wiki.service;

import com.wiki.model.dto.UserDTO;
import com.wiki.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAll() {
        List<UserDTO> result = new ArrayList<>();
        userRepository.findAll().forEach(user -> result.add(new UserDTO(user.getId(), user.getIdentity().getUsername(), user.getFullName())));
        return result;
    }

    public UserDTO getByUsername(String username) {
        return userRepository.findByUsername(username).map(user -> new UserDTO(user.getId(), user.getIdentity().getUsername(), user.getFullName()))
                .orElse(null);
    }
}
