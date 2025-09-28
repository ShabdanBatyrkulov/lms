package io.github.ShabdanBatyrkulov.lms.dto;

public class UserRegistrationDto {
    private String username;
    private String password;

    public UserRegistrationDto() {}

    public UserRegistrationDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
