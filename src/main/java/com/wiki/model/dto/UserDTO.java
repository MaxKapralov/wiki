package com.wiki.model.dto;

public class UserDTO {
    private Long id;
    private String username;
    private String fullName;


    public UserDTO(Long id, String username, String fullName) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
    }

    protected UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }
}
