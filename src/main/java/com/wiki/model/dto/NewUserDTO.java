package com.wiki.model.dto;

public class NewUserDTO {
    private String name;
    private String surname;
    private String email;
    private String password;

    protected NewUserDTO() {
    }

    public NewUserDTO(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
