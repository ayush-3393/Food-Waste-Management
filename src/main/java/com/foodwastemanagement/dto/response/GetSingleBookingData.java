package com.foodwastemanagement.dto.response;

import com.foodwastemanagement.models.enums.BookingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class GetSingleBookingData {
    private Long bookingId;
    private Long foodDonationId;
    private String foodName;
    private Double price;
    private LocalDateTime bookingCreatedTime;
    private BookingStatus bookingStatus;
}
