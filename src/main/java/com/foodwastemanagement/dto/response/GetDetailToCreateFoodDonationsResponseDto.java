package com.foodwastemanagement.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class GetDetailToCreateFoodDonationsResponseDto {
    private List<FoodCategoryResponseDto> foodCategories;
    private List<QuantityUnitResponseDto> quantityUnits;
}
