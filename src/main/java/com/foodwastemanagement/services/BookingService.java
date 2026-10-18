package com.foodwastemanagement.services;

import com.foodwastemanagement.models.Booking;
import com.foodwastemanagement.models.FoodDonation;
import com.foodwastemanagement.models.User;

public interface BookingService {
    Booking createBooking(User user, Long foodDonationId);
}
