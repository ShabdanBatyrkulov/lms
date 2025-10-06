package io.github.ShabdanBatyrkulov.lms.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users") // Overrides default table name "user"
public class User {

    @Id
    @GeneratedValue
    private UUID id;  // Primary key is now UUID

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    public User() {
    }

    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
