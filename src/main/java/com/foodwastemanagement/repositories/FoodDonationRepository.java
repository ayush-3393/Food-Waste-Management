package com.foodwastemanagement.repositories;

import com.foodwastemanagement.models.FoodDonation;
import com.foodwastemanagement.models.enums.FoodListingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FoodDonationRepository extends JpaRepository<FoodDonation, Long> {
    List<FoodDonation> findAllByDonorId(Long donorId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE FoodDonation f SET f.foodListingStatus = :status " +
            "WHERE f.foodListingStatus = :currentStatus AND f.bestBeforeTime <= :now")
    Integer markExpired(@Param("now") LocalDateTime now,
                    @Param("status") FoodListingStatus status,
                    @Param("currentStatus") FoodListingStatus currentStatus);

    @Query(value = """
            SELECT * FROM food_donations
            WHERE food_listing_status = 'AVAILABLE'
              AND best_before_time > :now
              AND latitude  BETWEEN :minLat AND :maxLat
              AND longitude BETWEEN :minLng AND :maxLng
              AND 6371 * acos(LEAST(1.0,
                    cos(radians(:lat)) * cos(radians(latitude))
                  * cos(radians(longitude) - radians(:lng))
                  + sin(radians(:lat)) * sin(radians(latitude)))) <= :radiusKm
            ORDER BY 6371 * acos(LEAST(1.0,
                    cos(radians(:lat)) * cos(radians(latitude))
                  * cos(radians(longitude) - radians(:lng))
                  + sin(radians(:lat)) * sin(radians(latitude))))
            LIMIT :limit
            """, nativeQuery = true)
    List<FoodDonation> findNearby(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("radiusKm") double radiusKm,
            @Param("minLat") double minLat,
            @Param("maxLat") double maxLat,
            @Param("minLng") double minLng,
            @Param("maxLng") double maxLng,
            @Param("now") LocalDateTime now,
            @Param("limit") int limit
    );
}
