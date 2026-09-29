package com.foodwastemanagement.services;

import com.foodwastemanagement.dto.request.LoginUserRequestDto;
import com.foodwastemanagement.dto.request.RegisterUserRequestDto;
import com.foodwastemanagement.models.User;

public interface UserService {
    User registerUser(RegisterUserRequestDto registerUserRequestDto);
    User loginUser(LoginUserRequestDto loginUserRequestDto);
}
