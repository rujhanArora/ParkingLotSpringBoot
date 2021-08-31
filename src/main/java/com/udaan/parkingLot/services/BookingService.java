package com.udaan.parkingLot.services;

import com.udaan.parkingLot.models.Booking;
import com.udaan.parkingLot.models.BookingStatus;
import com.udaan.parkingLot.models.parkingSpot.ParkingSpot;
import com.udaan.parkingLot.repositories.BookingRepository;
import com.udaan.parkingLot.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired SpotService spotService;
    @Autowired
    VehicleService vehicleService;

    public Booking checkIn(String customerId, String vehicleId, String parkingSpotId) {
        spotService.bookSpot(parkingSpotId);
        Booking booking = new Booking(customerId, vehicleId, parkingSpotId);
        log.info("Created booking {}", booking);
        return bookingRepository.addBooking(booking);
    }

    public Booking getBookingById(String bookingId) {
        return bookingRepository.getBookingById(bookingId);
    }

    public List<Booking> getBookingsByVehicleId(String vehicleId) {
        return bookingRepository.getByVehicleId(vehicleId);
    }

    public boolean payForBooking(String bookingId) {
        // Simulating Payment Service here
        Booking booking = getBookingById(bookingId);
        ParkingSpot parkingSpot = spotService.getSpotById(booking.getParkingSpotId());
        Date checkoutDate = new Date();
        Double paymentAmount = parkingSpot.getRateCard().calculateRate(booking.getCheckInAt(), checkoutDate);
        log.info("paymentAmount:  " + paymentAmount);
        booking.setAmountPaid(paymentAmount);
        booking.setStatus(BookingStatus.PAID);
        bookingRepository.updateBooking(booking);
        log.info("Paid for booking: " + booking);
        return true;
    }

    public boolean checkOut(String bookingId) {
        Booking booking = getBookingById(bookingId);
        ValidationUtil.ensureTrue(booking.getStatus().equals(BookingStatus.PAID), "Payment required for booking id: " + bookingId);
        spotService.vacateSpot(booking.getParkingSpotId());
        booking.setStatus(BookingStatus.CHECKED_OUT);
        booking.setCheckedOutAt(new Date());
        bookingRepository.updateBooking(booking);
        return true;
    }
}
