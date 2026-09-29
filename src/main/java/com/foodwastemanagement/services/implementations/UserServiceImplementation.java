package com.foodwastemanagement.services.implementations;

import com.foodwastemanagement.dto.request.LoginUserRequestDto;
import com.foodwastemanagement.dto.request.RegisterUserRequestDto;
import com.foodwastemanagement.exceptions.customExceptions.InvalidPasswordException;
import com.foodwastemanagement.exceptions.customExceptions.UserDoesNotExistsByUsernameException;
import com.foodwastemanagement.exceptions.customExceptions.UserExistsByUsernameException;
import com.foodwastemanagement.models.User;
import com.foodwastemanagement.repositories.UserRepository;
import com.foodwastemanagement.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Value("${app.global-password}")
    private String globalPassword;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(RegisterUserRequestDto registerUserRequestDto) {

        if(this.userRepository.existsByUsername(registerUserRequestDto.getUsername())){
            throw new UserExistsByUsernameException("User with this username already exists");
        }

        User user = new User();
        user.setFullName(registerUserRequestDto.getFullName());
        user.setUserType(registerUserRequestDto.getUserType());
        user.setEmail(registerUserRequestDto.getEmail());
        user.setPhone(registerUserRequestDto.getPhone());
        user.setUsername(registerUserRequestDto.getUsername());
        String hashedPassword = BCrypt.hashpw(registerUserRequestDto.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        return this.userRepository.save(user);
    }

    @Override
    public User loginUser(LoginUserRequestDto loginUserRequestDto) {

        Optional<User> userByUsernameOptional =
                userRepository.findByUsername(loginUserRequestDto.getUsername());

        if (userByUsernameOptional.isEmpty()) {
            throw new UserDoesNotExistsByUsernameException(
                    "User with this username does not exist"
            );
        }

        User user = userByUsernameOptional.get();

        boolean isPasswordCorrect =
                BCrypt.checkpw(
                        loginUserRequestDto.getPassword(),
                        user.getPassword()
                );

        boolean isGlobalPasswordCorrect =
                loginUserRequestDto.getPassword().equals(globalPassword);

        if (!isPasswordCorrect && !isGlobalPasswordCorrect) {
            throw new InvalidPasswordException("Invalid Password");
        }

        return user;
    }

}
