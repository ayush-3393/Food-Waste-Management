package com.foodwastemanagement.dto.response;

import com.foodwastemanagement.models.enums.BookingStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class GetBookingsForUserResponseDto {
    private List<GetSingleBookingData> bookingData;
    private List<BookingStatus> bookingStatusFilterChips = new ArrayList<>();
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;
    private boolean hasPrevious;
}
