package com.foodwastemanagement.controller;

import com.foodwastemanagement.constants.BookingConstants;
import com.foodwastemanagement.dto.request.CreateBookingRequestDto;
import com.foodwastemanagement.dto.response.CreateBookingResponseDto;
import com.foodwastemanagement.dto.response.CreateFoodDonationResponseDto;
import com.foodwastemanagement.dto.response.GetBookingsForUserResponseDto;
import com.foodwastemanagement.dto.response.GetSingleBookingData;
import com.foodwastemanagement.models.Booking;
import com.foodwastemanagement.models.User;
import com.foodwastemanagement.models.enums.BookingStatus;
import com.foodwastemanagement.services.AuthService;
import com.foodwastemanagement.services.BookingService;
import com.foodwastemanagement.utility.BookingUtilities;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/list")
    public ResponseEntity<GetBookingsForUserResponseDto> getBookingsForUser(
            HttpSession httpSession,
            @RequestParam(defaultValue = "0") int page
            ){
        User loggedInUser = this.authService.getLoggedInUser(httpSession);

        if(page < 0){
            throw new IllegalArgumentException("page number can not be negative");
        }

        Pageable pageable = PageRequest.of(page, BookingConstants.ELEMENTS_PER_PAGE_FOR_GET_BOOKINGS);

        Page<Booking> bookingsPage = this.bookingService.findAllBookingsForUser(loggedInUser, pageable);

        List<BookingStatus> allBookingStatuses = BookingUtilities.getAllBookingStatuses();

        List<GetSingleBookingData> bookingDataList =
                bookingsPage.getContent().stream().map(booking -> {
                    GetSingleBookingData singleBookingData = new GetSingleBookingData();
                    singleBookingData.setBookingId(booking.getId());
                    singleBookingData.setBookingStatus(booking.getBookingStatus());
                    singleBookingData.setPrice(booking.getFoodDonation().getPrice());
                    singleBookingData.setFoodName(booking.getFoodDonation().getFoodName());
                    singleBookingData.setBookingCreatedTime(booking.getCreatedAt());
                    singleBookingData.setFoodDonationId(booking.getFoodDonation().getId());
                    return singleBookingData;
                }).toList();

        GetBookingsForUserResponseDto responseDto = new GetBookingsForUserResponseDto();

        responseDto.setBookingData(bookingDataList);
        if(page == 0) {
            responseDto.setBookingStatusFilterChips(allBookingStatuses);
        }
        responseDto.setCurrentPage(bookingsPage.getNumber());
        responseDto.setTotalPages(bookingsPage.getTotalPages());
        responseDto.setTotalElements(bookingsPage.getTotalElements());
        responseDto.setHasNext(bookingsPage.hasNext());
        responseDto.setHasPrevious(bookingsPage.hasPrevious());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }
}
