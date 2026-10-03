package com.foodwastemanagement.services.implementations;

import com.foodwastemanagement.exceptions.customExceptions.ResourceNotFoundException;
import com.foodwastemanagement.models.FoodCategory;
import com.foodwastemanagement.repositories.FoodCategoryRepository;
import com.foodwastemanagement.services.FoodCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodCategoryServiceImplementation implements FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    public FoodCategoryServiceImplementation(
            FoodCategoryRepository foodCategoryRepository) {
        this.foodCategoryRepository = foodCategoryRepository;
    }

    @Override
    public FoodCategory createFoodCategory() {
        return null;
    }

    @Override
    public FoodCategory updateFoodCategory() {
        return null;
    }

    @Override
    public FoodCategory deleteFoodCategory() {
        return null;
    }

    @Override
    public List<FoodCategory> getAllFoodCategories() {

        List<FoodCategory> foodCategoryList = this.foodCategoryRepository.findAll();

        if(foodCategoryList.isEmpty()){
            throw new ResourceNotFoundException("Food Categories Not Found!");
        }

        return foodCategoryList;
    }
}
