package com.foodwastemanagement.services;

import com.foodwastemanagement.dto.request.CreateFoodDonationRequestDto;
import com.foodwastemanagement.models.FoodDonation;
import com.foodwastemanagement.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodDonationService {
    FoodDonation createAFoodDonation(
            CreateFoodDonationRequestDto createFoodDonationRequestDto,
            MultipartFile image,
            User user
    );
    FoodDonation updateAFoodDonation();
    FoodDonation deleteAFoodDonation();
    List<FoodDonation> getAllFoodDonationsForUser(User user);
}
