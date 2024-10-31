package com.example.Hotel.Service.Interface;

import com.example.Hotel.DTO.Response;
import com.example.Hotel.Entity.Booking;

public interface IBookingService {
    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);
}
