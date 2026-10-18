package com.foodwastemanagement.dto.response;

import com.foodwastemanagement.models.enums.FoodType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class NearbyFoodDonationResponseDto {
    private Long id;
    private String foodName;
    private String foodCategoryName;
    private Integer quantity;
    private String quantityUnitSymbol;
    private FoodType foodType;
    private LocalDateTime bestBeforeTime;
    private String image;
    private Double latitude;
    private Double longitude;
    private Double distanceKm;
}
