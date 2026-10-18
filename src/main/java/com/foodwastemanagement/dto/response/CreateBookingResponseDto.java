package com.foodwastemanagement.dto.response;

import com.foodwastemanagement.models.enums.BookingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CreateBookingResponseDto {
    private Long bookingId;
    private String foodName;
    private Double price;
    private BookingStatus bookingStatus;
}
