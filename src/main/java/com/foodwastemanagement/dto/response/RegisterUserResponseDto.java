package com.foodwastemanagement.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class RegisterUserResponseDto {
    private Long id;
    private String username;
    private String message;
}
