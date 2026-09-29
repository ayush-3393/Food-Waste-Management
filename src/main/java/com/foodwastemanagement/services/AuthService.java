package com.foodwastemanagement.services;

import com.foodwastemanagement.models.User;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    User getLoggedInUser(HttpSession httpSession);
    void checkIfUserAlreadyLoggedIn(HttpSession httpSession);
    void logout(HttpSession httpSession);
}
