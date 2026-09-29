package com.foodwastemanagement.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class BaseErrorResponse {
    private int status;
    private String message;
}
