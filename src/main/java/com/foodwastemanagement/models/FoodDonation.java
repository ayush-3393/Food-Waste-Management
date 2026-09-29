package com.foodwastemanagement.models;

import com.foodwastemanagement.models.enums.FoodListingStatus;
import com.foodwastemanagement.models.enums.FoodType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "food_donations")
public class FoodDonation extends  BaseModel{

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_category_id", nullable = false)
    private FoodCategory foodCategory;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quantity_unit_id", nullable = false)
    private QuantityUnit unit;

    @Column(name = "prepared_time", nullable = false)
    private LocalDateTime preparedTime;

    @Column(name = "best_before_time", nullable = false)
    private LocalDateTime bestBeforeTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_type", nullable = false)
    private FoodType foodType;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_listing_status", nullable = false)
    private FoodListingStatus foodListingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donor_id", nullable = false)
    private User donor;
}
