package com.taskmanagement.dto;

public class UserResponse {
    private Long id;
    private String name;
    private String email;

    public UserResponse(String email, Long id, String name) {
        this.email = email;
        this.id = id;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
