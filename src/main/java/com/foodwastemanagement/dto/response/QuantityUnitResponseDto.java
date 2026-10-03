package com.foodwastemanagement.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class QuantityUnitResponseDto {
    private Long id;
    private String name;
    private String symbol;
}
