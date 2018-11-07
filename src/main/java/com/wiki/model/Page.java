package com.wiki.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@Entity
public class Page extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User author;

    @Lob
    @NotEmpty
    private String content;

    @NotEmpty
    private String title;

    @NotNull
    private Instant timestamp;

    @NotNull
    @Column(unique = true)
    private String link;

    @NotNull
    @ManyToMany
    private Set<User> allowedToRead;

    protected Page() {
    }

    public Page(@NotNull User author, @NotEmpty String content, @NotEmpty String title, @NotNull Instant timestamp, @NotNull Set<User> allowedToRead) {
        this.author = author;
        this.content = content;
        this.title = title;
        this.timestamp = timestamp;
        this.allowedToRead = allowedToRead;
        this.link = title.replaceAll("\\s+", "");
    }

    public User getAuthor() {
        return author;
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

    public String getLink() {
        return link;
    }

    public Set<User> getAllowedToRead() {
        return allowedToRead;
    }

    public void setAllowedToRead(Set<User> allowedToRead) {
        this.allowedToRead = allowedToRead;
    }

    @Override
    public String toString() {
        return "Page{" +
                "author=" + author +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", timestamp=" + timestamp +
                ", allowedToRead=" + allowedToRead +
                '}';
    }
}
