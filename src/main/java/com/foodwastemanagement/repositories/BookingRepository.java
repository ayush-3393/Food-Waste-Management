package com.foodwastemanagement.repositories;

import com.foodwastemanagement.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByFoodDonationId(Long foodDonationId);
}
