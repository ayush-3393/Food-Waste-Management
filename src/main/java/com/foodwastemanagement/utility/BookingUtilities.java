package com.foodwastemanagement.utility;

import com.foodwastemanagement.models.enums.BookingStatus;

import java.util.Arrays;
import java.util.List;

public final class BookingUtilities {
    private BookingUtilities() {
        // private constructor
    }

    public static List<BookingStatus> getAllBookingStatuses(){
        return Arrays.stream(BookingStatus.values()).toList();
    }
}
