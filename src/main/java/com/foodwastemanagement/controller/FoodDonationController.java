package com.foodwastemanagement.controller;

import com.foodwastemanagement.dto.request.CreateFoodDonationRequestDto;
import com.foodwastemanagement.dto.response.CreateFoodDonationResponseDto;
import com.foodwastemanagement.models.FoodDonation;
import com.foodwastemanagement.models.User;
import com.foodwastemanagement.services.AuthService;
import com.foodwastemanagement.services.FoodDonationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/food-donations")
public class FoodDonationController {

    private final AuthService authService;
    private final FoodDonationService foodDonationService;

    public FoodDonationController(
            AuthService authService,
            FoodDonationService foodDonationService) {
        this.authService = authService;
        this.foodDonationService = foodDonationService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateFoodDonationResponseDto> createFoodDonation (
            @Valid @RequestBody CreateFoodDonationRequestDto createFoodDonationRequestDto,
            HttpSession httpSession
            ){
        // throws exception if not logged in
        User loggedInUser = this.authService.getLoggedInUser(httpSession);

        FoodDonation foodDonation = this.foodDonationService.createAFoodDonation(
                createFoodDonationRequestDto,
                loggedInUser
        );

        CreateFoodDonationResponseDto responseDto = new CreateFoodDonationResponseDto();
        responseDto.setCreatedAt(foodDonation.getCreatedAt());
        responseDto.setFoodName(foodDonation.getFoodName());
        responseDto.setFoodCategoryName(foodDonation.getFoodCategory().getName());
        responseDto.setFoodListingStatus(foodDonation.getFoodListingStatus());
        responseDto.setFoodType(foodDonation.getFoodType());
        responseDto.setImage(foodDonation.getImage());
        responseDto.setQuantityUnitSymbol(foodDonation.getUnit().getSymbol());
        responseDto.setBestBeforeTime(foodDonation.getBestBeforeTime());
        responseDto.setPreparedTime(foodDonation.getPreparedTime());
        responseDto.setDescription(foodDonation.getDescription());
        responseDto.setQuantity(foodDonation.getQuantity());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }
}
