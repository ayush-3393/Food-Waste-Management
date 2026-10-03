package com.foodwastemanagement.services;

import com.foodwastemanagement.models.FoodCategory;

import java.util.List;

public interface FoodCategoryService {
    FoodCategory createFoodCategory();
    FoodCategory updateFoodCategory();
    FoodCategory deleteFoodCategory();
    List<FoodCategory> getAllFoodCategories();
}
