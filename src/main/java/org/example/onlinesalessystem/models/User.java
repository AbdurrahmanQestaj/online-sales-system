package org.example.onlinesalessystem.models;

public class User {
    private int id;
    private String fullName;
    private String email;
    private String username;
    private String passwordHash;
    private String salt;
    private String role;

    public User(int id, String fullName, String email, String username, String passwordHash, String salt, String role){
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.role = role;
    }

    public User(String fullName, String email, String username, String passwordHash, String salt, String role) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public String getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }
}
