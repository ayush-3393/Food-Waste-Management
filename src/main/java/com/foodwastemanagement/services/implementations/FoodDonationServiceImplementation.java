package com.foodwastemanagement.services.implementations;

import com.foodwastemanagement.constants.FoodDonationsConstants;
import com.foodwastemanagement.dto.request.CreateFoodDonationRequestDto;
import com.foodwastemanagement.dto.response.NearbyFoodDonationResponseDto;
import com.foodwastemanagement.exceptions.customExceptions.InvalidUserException;
import com.foodwastemanagement.exceptions.customExceptions.ResourceNotFoundException;
import com.foodwastemanagement.models.*;
import com.foodwastemanagement.models.enums.FoodListingStatus;
import com.foodwastemanagement.models.enums.UserType;
import com.foodwastemanagement.repositories.FoodCategoryRepository;
import com.foodwastemanagement.repositories.FoodDonationRepository;
import com.foodwastemanagement.repositories.QuantityUnitRepository;
import com.foodwastemanagement.services.FoodDonationService;
import com.foodwastemanagement.services.ImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FoodDonationServiceImplementation implements FoodDonationService {

    private final FoodDonationRepository foodDonationRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final QuantityUnitRepository quantityUnitRepository;
    private final ImageStorageService imageStorageService;

    public FoodDonationServiceImplementation(
            FoodDonationRepository foodDonationRepository,
            FoodCategoryRepository foodCategoryRepository,
            QuantityUnitRepository quantityUnitRepository,
            ImageStorageService imageStorageService) {
        this.foodDonationRepository = foodDonationRepository;
        this.foodCategoryRepository = foodCategoryRepository;
        this.quantityUnitRepository = quantityUnitRepository;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public FoodDonation createAFoodDonation(
            CreateFoodDonationRequestDto createFoodDonationRequestDto,
            MultipartFile image,
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

        String imagePath = this.imageStorageService.storeImage(image);
        foodDonation.setImage(imagePath);

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
        foodDonation.setCoordinates(createFoodDonationRequestDto.getCoordinates());

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

    @Override
    public List<FoodDonation> getAllFoodDonationsForUser(User user) {
        Long donorId = user.getId();
        return this.foodDonationRepository.findAllByDonorId(donorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NearbyFoodDonationResponseDto> getNearbyFoodDonations(Coordinates coordinates) {

        if (
                coordinates.getLatitude() < -90
                        || coordinates.getLatitude() > 90
                        || coordinates.getLongitude() < -180
                        || coordinates.getLongitude() > 180
        ) {
            throw new IllegalArgumentException("Invalid coordinates");
        }

        double radiusKm = FoodDonationsConstants.RADIUS_IN_KM_FOR_NEARBY_DONATIONS_SEARCH;
        Integer limit = FoodDonationsConstants.LIMIT_FOR_NEARBY_DONATIONS_NUMBER;

        double lat = coordinates.getLatitude();
        double lng = coordinates.getLongitude();

        // Bounding box (1 degree of latitude ≈ 111 km)
        double latDelta = radiusKm / 111.0;
        double lngDelta = radiusKm / (111.0 * Math.max(Math.cos(Math.toRadians(lat)), 0.0001));

        List<FoodDonation> donations = foodDonationRepository.findNearby(
                lat,
                lng,
                radiusKm,
                lat - latDelta,
                lat + latDelta,
                lng - lngDelta,
                lng + lngDelta,
                LocalDateTime.now(),
                limit);


        return donations.stream().map(d -> {
            NearbyFoodDonationResponseDto dto = new NearbyFoodDonationResponseDto();
            dto.setId(d.getId());
            dto.setFoodName(d.getFoodName());
            dto.setFoodCategoryName(d.getFoodCategory().getName());
            dto.setQuantity(d.getQuantity());
            dto.setQuantityUnitSymbol(d.getUnit().getSymbol());
            dto.setFoodType(d.getFoodType());
            dto.setBestBeforeTime(d.getBestBeforeTime());
            dto.setImage(d.getImage());
            dto.setLatitude(d.getCoordinates().getLatitude());
            dto.setLongitude(d.getCoordinates().getLongitude());
            dto.setDistanceKm(haversineKm(lat, lng,
                    d.getCoordinates().getLatitude(), d.getCoordinates().getLongitude()));
            return dto;
        }).toList();
    }

    private boolean isFoodListedForDonationExpired(LocalDateTime bestBeforeTime) {
        return !bestBeforeTime.isAfter(LocalDateTime.now());
    }

    private static double haversineKm(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        return 6371 * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
