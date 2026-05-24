package org.example.onlinesalessystem.models.dto;

public class SignupDto {

    private String fullName;
    private String email;
    private String username;
    private String password;
    private String confirmPassword;

    public SignupDto(String fullName, String email, String username, String password, String confirmPassword) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getFullName(){
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
}
