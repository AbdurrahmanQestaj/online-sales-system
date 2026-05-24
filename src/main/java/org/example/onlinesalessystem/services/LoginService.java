package org.example.onlinesalessystem.services;

import org.example.onlinesalessystem.models.User;
import org.example.onlinesalessystem.models.dto.LoginDto;
import org.example.onlinesalessystem.repository.UserRepository;

public class LoginService {
    private final UserRepository userRepository;

    public LoginService(){
        this.userRepository = new UserRepository();
    }

    public User login(LoginDto loginDto) throws Exception {
        if (loginDto.getUsername() == null || loginDto.getUsername().trim().isEmpty()) {
            throw new Exception("Username nuk duhet te jete bosh.");
        }

        if (loginDto.getPassword() == null || loginDto.getPassword().trim().isEmpty()) {
            throw new Exception("Password nuk duhet te jete bosh.");
        }

        User user = userRepository.getByUsername(loginDto.getUsername());

        if (user == null) {
            throw new Exception("User nuk ekziston.");
        }

        boolean passwordValid = HashService.verifyPassword(
                loginDto.getPassword(),
                user.getSalt(),
                user.getPasswordHash()
        );

        if (!passwordValid) {
            throw new Exception("Password eshte gabim.");
        }

        return user;
    }
}
