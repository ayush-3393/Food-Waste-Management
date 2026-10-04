package com.foodwastemanagement.repositories;

import com.foodwastemanagement.models.FoodDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodDonationRepository extends JpaRepository<FoodDonation, Long> {
    List<FoodDonation> findAllByDonorId(Long donorId);
}
