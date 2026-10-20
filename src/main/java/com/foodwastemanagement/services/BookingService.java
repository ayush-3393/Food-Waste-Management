package com.foodwastemanagement.services;

import com.foodwastemanagement.models.Booking;
import com.foodwastemanagement.models.FoodDonation;
import com.foodwastemanagement.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    Booking createBooking(User user, Long foodDonationId);
    Page<Booking> findAllBookingsForUser(User user, Pageable pageable);
}
