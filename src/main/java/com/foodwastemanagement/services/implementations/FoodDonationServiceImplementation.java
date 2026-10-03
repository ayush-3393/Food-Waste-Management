package com.foodwastemanagement.services.implementations;

import com.foodwastemanagement.dto.request.CreateFoodDonationRequestDto;
import com.foodwastemanagement.exceptions.customExceptions.InvalidUserException;
import com.foodwastemanagement.exceptions.customExceptions.ResourceNotFoundException;
import com.foodwastemanagement.models.FoodCategory;
import com.foodwastemanagement.models.FoodDonation;
import com.foodwastemanagement.models.QuantityUnit;
import com.foodwastemanagement.models.User;
import com.foodwastemanagement.models.enums.FoodListingStatus;
import com.foodwastemanagement.models.enums.UserType;
import com.foodwastemanagement.repositories.FoodCategoryRepository;
import com.foodwastemanagement.repositories.FoodDonationRepository;
import com.foodwastemanagement.repositories.QuantityUnitRepository;
import com.foodwastemanagement.services.FoodDonationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FoodDonationServiceImplementation implements FoodDonationService {

    private final FoodDonationRepository foodDonationRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final QuantityUnitRepository quantityUnitRepository;

    public FoodDonationServiceImplementation(
            FoodDonationRepository foodDonationRepository,
            FoodCategoryRepository foodCategoryRepository,
            QuantityUnitRepository quantityUnitRepository) {
        this.foodDonationRepository = foodDonationRepository;
        this.foodCategoryRepository = foodCategoryRepository;
        this.quantityUnitRepository = quantityUnitRepository;
    }

    @Override
    public FoodDonation createAFoodDonation(
            CreateFoodDonationRequestDto createFoodDonationRequestDto,
            User user) {

        if(user.getUserType() == UserType.DELIVERY_PARTNER){
            throw new InvalidUserException("Delivery partners are not allowed to create food donations");
        }

        FoodDonation foodDonation = new FoodDonation();
        foodDonation.setFoodName(createFoodDonationRequestDto.getFoodName());

        Optional<FoodCategory> foodCategoryOptional =
                this.foodCategoryRepository
                        .findById(createFoodDonationRequestDto.getFoodCategoryId());

        if(foodCategoryOptional.isEmpty()){
            throw new ResourceNotFoundException("Food Category was not found!");
        }

        foodDonation.setFoodCategory(foodCategoryOptional.get());
        foodDonation.setFoodType(createFoodDonationRequestDto.getFoodType());

        foodDonation.setFoodListingStatus(
                isFoodListedForDonationExpired(createFoodDonationRequestDto.getBestBeforeTime())
                        ? FoodListingStatus.EXPIRED : FoodListingStatus.AVAILABLE
        );

        foodDonation.setBestBeforeTime(createFoodDonationRequestDto.getBestBeforeTime());
        foodDonation.setDonor(user);
        foodDonation.setImage(createFoodDonationRequestDto.getImage());

        Optional<QuantityUnit> quantityUnitOptional =
                this.quantityUnitRepository
                        .findById(createFoodDonationRequestDto.getUnitId());

        if(quantityUnitOptional.isEmpty()){
            throw new ResourceNotFoundException("Quantity Unit was not found!");
        }

        foodDonation.setUnit(quantityUnitOptional.get());
        foodDonation.setPreparedTime(createFoodDonationRequestDto.getPreparedTime());
        foodDonation.setQuantity(createFoodDonationRequestDto.getQuantity());
        foodDonation.setDescription(createFoodDonationRequestDto.getDescription());

        return this.foodDonationRepository.save(foodDonation);
    }

    @Override
    public FoodDonation updateAFoodDonation() {
        return null;
    }

    @Override
    public FoodDonation deleteAFoodDonation() {
        return null;
    }

    private boolean isFoodListedForDonationExpired(LocalDateTime bestBeforeTime){
        LocalDate currentDate = LocalDate.now();
        LocalDate bestBeforeDate = bestBeforeTime.toLocalDate();
        return currentDate.isAfter(bestBeforeDate);
    }
}
