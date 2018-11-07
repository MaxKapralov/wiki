package com.wiki.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Identity extends BaseEntity {

    @NotNull
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles;
    @NotEmpty
    @Column(unique = true)
    private String username;
    @NotEmpty
    private String password;

    protected Identity() {
    }

    public Identity(@NotEmpty String username, @NotEmpty String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
