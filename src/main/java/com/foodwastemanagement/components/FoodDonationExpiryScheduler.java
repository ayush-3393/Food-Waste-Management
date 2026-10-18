package com.foodwastemanagement.components;

import com.foodwastemanagement.models.enums.FoodListingStatus;
import com.foodwastemanagement.repositories.FoodDonationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class FoodDonationExpiryScheduler {

    private final FoodDonationRepository foodDonationRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void markExpiredDonations() {
        Integer updated = foodDonationRepository.markExpired(
                LocalDateTime.now(),
                FoodListingStatus.EXPIRED,
                FoodListingStatus.AVAILABLE
        );
        if (updated > 0) {
            log.info("Marked {} food donations as expired", updated);
        }
    }
}
