package com.wiki.model.dto;

import java.time.Instant;
import java.util.Set;

public class PageDTO {
    private Long id;

    private String content;

    private String title;

    private Instant timestamp;

    private UserDTO author;

    private Set<UserDTO> allowedToRead;

    public PageDTO(Long id, String content, String title, Instant timestamp, UserDTO author, Set<UserDTO> allowedToRead) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.timestamp = timestamp;
        this.author = author;
        this.allowedToRead = allowedToRead;
    }

    protected PageDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public Set<UserDTO> getAllowedToRead() {
        return allowedToRead;
    }
}
