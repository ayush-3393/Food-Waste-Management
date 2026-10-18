package com.foodwastemanagement.models;

import com.foodwastemanagement.models.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "bookings")
public class Booking extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "food_donation_id", nullable = false, unique = true)
    private FoodDonation foodDonation;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
}
