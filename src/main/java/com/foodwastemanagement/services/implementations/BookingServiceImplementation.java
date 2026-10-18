package com.foodwastemanagement.services.implementations;

import com.foodwastemanagement.exceptions.customExceptions.InvalidFoodListingStatusException;
import com.foodwastemanagement.exceptions.customExceptions.ResourceAlreadyExistsException;
import com.foodwastemanagement.exceptions.customExceptions.ResourceNotFoundException;
import com.foodwastemanagement.models.Booking;
import com.foodwastemanagement.models.FoodDonation;
import com.foodwastemanagement.models.User;
import com.foodwastemanagement.models.enums.BookingStatus;
import com.foodwastemanagement.models.enums.FoodListingStatus;
import com.foodwastemanagement.repositories.BookingRepository;
import com.foodwastemanagement.repositories.FoodDonationRepository;
import com.foodwastemanagement.services.BookingService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingServiceImplementation implements BookingService {

    private final BookingRepository bookingRepository;
    private final FoodDonationRepository foodDonationRepository;

    public BookingServiceImplementation(BookingRepository bookingRepository,
                                        FoodDonationRepository foodDonationRepository) {
        this.bookingRepository = bookingRepository;
        this.foodDonationRepository = foodDonationRepository;
    }

    @Override
    public Booking createBooking(User user, Long foodDonationId) {

        Optional<FoodDonation> optionalFoodDonation = this.foodDonationRepository.findById(foodDonationId);

        if(optionalFoodDonation.isEmpty()){
            throw new ResourceNotFoundException("Food Donation Not Found");
        }

        FoodDonation foodDonation = optionalFoodDonation.get();

        if(foodDonation.getFoodListingStatus() != FoodListingStatus.AVAILABLE){
            throw new InvalidFoodListingStatusException("The food donation is not available");
        }

        //check for existing bookings for same food donation id
        Optional<Booking> bookingOptional = this.bookingRepository.findByFoodDonationId(foodDonationId);
        if(bookingOptional.isPresent()){
            throw new ResourceAlreadyExistsException(
                    "A booking for food donation ID " + foodDonationId + " already exists"
            );
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setFoodDonation(foodDonation);
        booking.setBookingStatus(BookingStatus.PENDING);

        return this.bookingRepository.save(booking);
    }
}
