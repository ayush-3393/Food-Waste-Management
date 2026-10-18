package com.foodwastemanagement.controller;

import com.foodwastemanagement.dto.request.CreateBookingRequestDto;
import com.foodwastemanagement.dto.response.CreateBookingResponseDto;
import com.foodwastemanagement.dto.response.CreateFoodDonationResponseDto;
import com.foodwastemanagement.models.Booking;
import com.foodwastemanagement.models.User;
import com.foodwastemanagement.services.AuthService;
import com.foodwastemanagement.services.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final AuthService authService;

    public BookingController(BookingService bookingService, AuthService authService) {
        this.bookingService = bookingService;
        this.authService = authService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateBookingResponseDto> createBooking(
            @RequestBody CreateBookingRequestDto requestDto,
            HttpSession httpSession){
        User loggedInUser = this.authService.getLoggedInUser(httpSession);

        Booking booking = this.bookingService.createBooking(loggedInUser, requestDto.getFoodDonationId());

        CreateBookingResponseDto responseDto = new CreateBookingResponseDto();
        responseDto.setBookingId(booking.getId());
        responseDto.setFoodName(booking.getFoodDonation().getFoodName());
        responseDto.setPrice(booking.getFoodDonation().getPrice());
        responseDto.setBookingStatus(booking.getBookingStatus());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
