package com.foodwastemanagement.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class FoodCategoryResponseDto {
    private Long id;
    private String name;
    private String description;
}
