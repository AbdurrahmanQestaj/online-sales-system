package org.example.onlinesalessystem.services;

import org.example.onlinesalessystem.models.User;
import org.example.onlinesalessystem.models.dto.SignupDto;
import org.example.onlinesalessystem.repository.UserRepository;

public class SignupService {
    private final UserRepository userRepository;

    public SignupService() {
        this.userRepository = new UserRepository();
    }

    public User signup(SignupDto signupDto) throws Exception {
        validateSignup(signupDto);

        if (userRepository.getByUsername(signupDto.getUsername()) != null) {
            throw new Exception("Ky username ekziston.");
        }

        if (userRepository.getByEmail(signupDto.getEmail()) != null) {
            throw new Exception("Ky email ekziston.");
        }

        String salt = HashService.generateSalt();
        String passwordHash = HashService.hashPassword(signupDto.getPassword(), salt);

        User user = new User(
                signupDto.getFullName(),
                signupDto.getEmail(),
                signupDto.getUsername(),
                passwordHash,
                salt,
                "User"
        );

        return userRepository.create(user);
    }

    private void validateSignup(SignupDto signupDto) throws Exception {
        if (signupDto.getFullName() == null || signupDto.getFullName().trim().isEmpty()) {
            throw new Exception("Emri nuk duhet te jete bosh.");
        }

        if (signupDto.getEmail() == null || !signupDto.getEmail().contains("@")) {
            throw new Exception("Email nuk eshte valid.");
        }

        if (signupDto.getUsername() == null || signupDto.getUsername().trim().isEmpty()) {
            throw new Exception("Username nuk duhte te jete bosh.");
        }

        if (signupDto.getPassword() == null || signupDto.getPassword().length() < 6) {
            throw new Exception("Password duhte te kete se paku 6 karaktere.");
        }

        if (!signupDto.getPassword().equals(signupDto.getConfirmPassword())) {
            throw new Exception("Password-at nuk perputhen.");
        }
    }
}
