package com.foodwastemanagement.dto.response;

import com.foodwastemanagement.models.FoodCategory;
import com.foodwastemanagement.models.QuantityUnit;
import com.foodwastemanagement.models.enums.FoodListingStatus;
import com.foodwastemanagement.models.enums.FoodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class CreateFoodDonationResponseDto {
    private String foodName;
    private String foodCategoryName;
    private Integer quantity;
    private String quantityUnitSymbol;
    private LocalDateTime preparedTime;
    private LocalDateTime bestBeforeTime;
    private FoodType foodType;
    private String description;
    private String image;
    private FoodListingStatus foodListingStatus;
    private LocalDateTime createdAt;
}
