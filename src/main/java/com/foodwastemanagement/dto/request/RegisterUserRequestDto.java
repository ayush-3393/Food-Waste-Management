package com.foodwastemanagement.dto.request;

import com.foodwastemanagement.models.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegisterUserRequestDto {
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String phone;
    private UserType userType;
}
