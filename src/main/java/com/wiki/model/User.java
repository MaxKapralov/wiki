package com.wiki.model;

import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class User extends BaseEntity {
    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @OneToOne()
    @MapsId
    private Identity identity;

    protected User() {
    }

    public User(@NotEmpty String name, @NotEmpty String surname, Identity identity) {
        this.name = name;
        this.surname = surname;
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Identity getIdentity() {
        return identity;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", identity=" + identity +
                '}';
    }
}
