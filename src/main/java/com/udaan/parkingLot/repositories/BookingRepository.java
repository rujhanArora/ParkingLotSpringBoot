package com.udaan.parkingLot.repositories;

import com.udaan.parkingLot.models.Booking;

import java.util.List;

public interface BookingRepository {
    Booking addBooking(Booking booking);
    Booking getBookingById(String bookingId);
    Booking updateBooking(Booking booking);
    List<Booking> getByVehicleId(String vehicleId);
}
