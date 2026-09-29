package com.foodwastemanagement.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginUserRequestDto {
    private String username;
    private String password;
}
