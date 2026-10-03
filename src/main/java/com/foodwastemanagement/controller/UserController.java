package com.foodwastemanagement.controller;

import com.foodwastemanagement.constants.SessionConstants;
import com.foodwastemanagement.dto.request.LoginUserRequestDto;
import com.foodwastemanagement.dto.request.RegisterUserRequestDto;
import com.foodwastemanagement.dto.response.GetLoggedInUserResponseDto;
import com.foodwastemanagement.dto.response.LoginUserResponseDto;
import com.foodwastemanagement.dto.response.LogoutUserResponseDto;
import com.foodwastemanagement.dto.response.RegisterUserResponseDto;
import com.foodwastemanagement.models.User;
import com.foodwastemanagement.services.AuthService;
import com.foodwastemanagement.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> registerUser(
            @Valid @RequestBody RegisterUserRequestDto registerUserRequestDto,
            HttpSession httpSession){
        User registeredUser = this.userService.registerUser(registerUserRequestDto);

        httpSession.setAttribute(SessionConstants.USER_ID_ATTRIBUTE, registeredUser.getId());

        RegisterUserResponseDto registerUserResponseDto = new RegisterUserResponseDto();
        registerUserResponseDto.setId(registeredUser.getId());
        registerUserResponseDto.setUsername(registeredUser.getUsername());
        registerUserResponseDto.setMessage(
                "Welcome! Registration was successful " + registeredUser.getUsername()
        );

        return new ResponseEntity<>(registerUserResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> loginUser(
            @Valid @RequestBody LoginUserRequestDto loginUserRequestDto,
            HttpSession httpSession){

        // throws exception if already logged in
        this.authService.checkIfUserAlreadyLoggedIn(httpSession);

        User loggedInUser = this.userService.loginUser(loginUserRequestDto);

        httpSession.setAttribute(SessionConstants.USER_ID_ATTRIBUTE, loggedInUser.getId());

        LoginUserResponseDto loginUserResponseDto = new LoginUserResponseDto();
        loginUserResponseDto.setMessage("Welcome " + loggedInUser.getUsername());

        return new ResponseEntity<>(loginUserResponseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutUserResponseDto> logoutUser(HttpSession httpSession){
        this.authService.logout(httpSession);

        LogoutUserResponseDto logoutUserResponseDto = new LogoutUserResponseDto();
        logoutUserResponseDto.setMessage("You were successfully logged out!");

        return new ResponseEntity<>(logoutUserResponseDto, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<GetLoggedInUserResponseDto> getLoggedInUser(HttpSession httpSession) {

        User loggedInUser = this.authService.getLoggedInUser(httpSession);

        GetLoggedInUserResponseDto getLoggedInUserResponseDto = new GetLoggedInUserResponseDto();
        getLoggedInUserResponseDto.setUsername(loggedInUser.getUsername());

        return new ResponseEntity<>(getLoggedInUserResponseDto, HttpStatus.OK);
    }
}
