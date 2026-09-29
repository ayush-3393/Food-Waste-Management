package com.foodwastemanagement.services;

import com.foodwastemanagement.dto.request.CreateFoodDonationRequestDto;
import com.foodwastemanagement.models.FoodDonation;
import com.foodwastemanagement.models.User;

public interface FoodDonationService {
    FoodDonation createAFoodDonation(
            CreateFoodDonationRequestDto createFoodDonationRequestDto,
            User user
    );
    FoodDonation updateAFoodDonation();
    FoodDonation deleteAFoodDonation();
}
