package org.example.onlinesalessystem.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class HashService {
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateSalt() {
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            String saltedPassword = password + salt;

            byte [] hashedBytes = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Gabim gjate hashimit te password-it", e);
        }
    }

    public static boolean verifyPassword(String password, String salt, String storedHash) {
        String newHash = hashPassword(password, salt);
        return newHash.equals(storedHash);
    }
}
