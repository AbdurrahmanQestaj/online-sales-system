package org.example.onlinesalessystem.utils;

import org.example.onlinesalessystem.models.User;

public class SessionManager {
    private static User currentUser;

    public static void login(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static int getCurrentUserId() {
        if (currentUser == null) {
            return -1;
        }

        return currentUser.getId();
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
    }
}
