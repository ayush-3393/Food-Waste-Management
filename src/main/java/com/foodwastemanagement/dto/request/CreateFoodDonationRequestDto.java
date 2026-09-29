package com.foodwastemanagement.dto.request;

import com.foodwastemanagement.models.FoodCategory;
import com.foodwastemanagement.models.QuantityUnit;
import com.foodwastemanagement.models.enums.FoodType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class CreateFoodDonationRequestDto {
    @NotBlank(message = "food name is required")
    @Size(max = 100, message = "food name must not exceed 100 characters")
    private String foodName;

    @NotNull(message = "food category must be selected")
    private Long foodCategoryId;

    @NotNull(message = "quantity must be specified")
    @Min(value = 1, message = "quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "mention the unit")
    private Long unitId;

    @NotNull(message = "prepared time must be provided")
    private LocalDateTime preparedTime;

    @NotNull(message = "best before time must be provided")
    private LocalDateTime bestBeforeTime;

    @NotNull(message = "food type must be provided")
    private FoodType foodType;

    @Size(max = 500, message = "description must not exceed 500 characters")
    private String description;

    private String image;
}
