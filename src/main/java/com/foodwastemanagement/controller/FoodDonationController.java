package com.foodwastemanagement.controller;

import com.foodwastemanagement.dto.request.CreateFoodDonationRequestDto;
import com.foodwastemanagement.dto.response.CreateFoodDonationResponseDto;
import com.foodwastemanagement.dto.response.FoodCategoryResponseDto;
import com.foodwastemanagement.dto.response.GetDetailToCreateFoodDonationsResponseDto;
import com.foodwastemanagement.dto.response.QuantityUnitResponseDto;
import com.foodwastemanagement.models.FoodCategory;
import com.foodwastemanagement.models.FoodDonation;
import com.foodwastemanagement.models.QuantityUnit;
import com.foodwastemanagement.models.User;
import com.foodwastemanagement.services.AuthService;
import com.foodwastemanagement.services.FoodCategoryService;
import com.foodwastemanagement.services.FoodDonationService;
import com.foodwastemanagement.services.QuantityUnitService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/food-donations")
public class FoodDonationController {

    private final AuthService authService;
    private final FoodDonationService foodDonationService;
    private final FoodCategoryService foodCategoryService;
    private final QuantityUnitService quantityUnitService;

    public FoodDonationController(
            AuthService authService,
            FoodDonationService foodDonationService,
            FoodCategoryService foodCategoryService,
            QuantityUnitService quantityUnitService) {
        this.authService = authService;
        this.foodDonationService = foodDonationService;
        this.foodCategoryService = foodCategoryService;
        this.quantityUnitService = quantityUnitService;
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateFoodDonationResponseDto> createFoodDonation(
            @Valid @ModelAttribute CreateFoodDonationRequestDto requestDto,
            @RequestParam(value = "image", required = false)
            MultipartFile image,
            HttpSession httpSession
    ) {
        User loggedInUser =
                this.authService.getLoggedInUser(httpSession);

        FoodDonation foodDonation =
                this.foodDonationService.createAFoodDonation(requestDto, image, loggedInUser);

        CreateFoodDonationResponseDto responseDto = new CreateFoodDonationResponseDto();

        responseDto.setCreatedAt(foodDonation.getCreatedAt());
        responseDto.setFoodName(foodDonation.getFoodName());
        responseDto.setFoodCategoryName(foodDonation.getFoodCategory().getName());
        responseDto.setFoodListingStatus(foodDonation.getFoodListingStatus());
        responseDto.setFoodType(foodDonation.getFoodType());
        String imagePath = foodDonation.getImage();
        if (imagePath != null) {
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(imagePath)
                    .toUriString();
            responseDto.setImage(imageUrl);
        }
        responseDto.setQuantityUnitSymbol(foodDonation.getUnit().getSymbol());
        responseDto.setBestBeforeTime(foodDonation.getBestBeforeTime());
        responseDto.setPreparedTime(foodDonation.getPreparedTime());
        responseDto.setDescription(foodDonation.getDescription());
        responseDto.setQuantity(foodDonation.getQuantity());

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/details")
    public ResponseEntity<GetDetailToCreateFoodDonationsResponseDto>
    getDetailToCreateFoodDonationsResponseDtoResponseEntity(HttpSession httpSession){
        // Throws exception if the user is NOT logged in
        this.authService.getLoggedInUser(httpSession);

        List<FoodCategory> foodCategories = this.foodCategoryService.getAllFoodCategories();

        List<FoodCategoryResponseDto> foodCategoryResponseDtos = new ArrayList<>();
        for(FoodCategory foodCategory : foodCategories){
            FoodCategoryResponseDto foodCategoryResponseDto = new FoodCategoryResponseDto();
            foodCategoryResponseDto.setId(foodCategory.getId());
            foodCategoryResponseDto.setName(foodCategory.getName());
            foodCategoryResponseDto.setDescription(foodCategory.getDescription());
            foodCategoryResponseDtos.add(foodCategoryResponseDto);
        }

        List<QuantityUnit> listOfQuantityUnits = this.quantityUnitService.getListOfQuantityUnits();

        List<QuantityUnitResponseDto> quantityUnitResponseDtos = new ArrayList<>();
        for(QuantityUnit quantityUnit: listOfQuantityUnits){
            QuantityUnitResponseDto quantityUnitResponseDto = new QuantityUnitResponseDto();
            quantityUnitResponseDto.setId(quantityUnit.getId());
            quantityUnitResponseDto.setName(quantityUnit.getName());
            quantityUnitResponseDto.setSymbol(quantityUnit.getSymbol());
            quantityUnitResponseDtos.add(quantityUnitResponseDto);
        }

        GetDetailToCreateFoodDonationsResponseDto responseDto = new GetDetailToCreateFoodDonationsResponseDto();
        responseDto.setFoodCategories(foodCategoryResponseDtos);
        responseDto.setQuantityUnits(quantityUnitResponseDtos);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
