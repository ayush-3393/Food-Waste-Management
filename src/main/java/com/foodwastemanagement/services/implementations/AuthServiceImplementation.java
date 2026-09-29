package com.foodwastemanagement.services.implementations;

import com.foodwastemanagement.constants.SessionConstants;
import com.foodwastemanagement.exceptions.customExceptions.UserAlreadyLoggedInException;
import com.foodwastemanagement.exceptions.customExceptions.UserNotLoggedInException;
import com.foodwastemanagement.models.User;
import com.foodwastemanagement.repositories.UserRepository;
import com.foodwastemanagement.services.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImplementation implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getLoggedInUser(HttpSession httpSession) {
        Object userId = httpSession.getAttribute(SessionConstants.USER_ID_ATTRIBUTE);
        if (userId == null) {
            throw new UserNotLoggedInException("Please login first");
        }

        Optional<User> userByIdOptional = this.userRepository.findById((Long) userId);

        if(userByIdOptional.isEmpty()){
            throw new UserNotLoggedInException("User Not Found!");
        }

        return userByIdOptional.get();
    }

    @Override
    public void checkIfUserAlreadyLoggedIn(HttpSession httpSession) {
        Object userId = httpSession.getAttribute(SessionConstants.USER_ID_ATTRIBUTE);

        if (userId != null) {
            throw new UserAlreadyLoggedInException(
                    "User is already logged in"
            );
        }
    }

    @Override
    public void logout(HttpSession httpSession) {
        Object userId = httpSession.getAttribute(SessionConstants.USER_ID_ATTRIBUTE);
        if (userId == null) {
            throw new UserNotLoggedInException(
                    "User is not logged in"
            );
        }
        httpSession.invalidate();
    }
}
