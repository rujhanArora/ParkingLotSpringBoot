package com.udaan.parkingLot.repositories;

import com.udaan.parkingLot.models.Booking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class BookingRepositoryInMemoryImpl implements BookingRepository {
    private List<Booking> bookings = new ArrayList<>();
    private Map<String, Integer> idToBookingIndex = new HashMap<>();
    private Map<String, List<Integer>> customerIdToBookingsIndex = new HashMap<>();
    private Map<String, List<Integer>> vehicleIdToBookingsIndex = new HashMap<>();

    @Override
    public Booking addBooking(Booking booking) {
        bookings.add(booking);
        int bookingIndex = bookings.size() - 1;
        vehicleIdToBookingsIndex.computeIfAbsent(booking.getVehicleId(), bookingIndices -> new ArrayList<>())
                .add(bookingIndex);
        idToBookingIndex.put(booking.getId(), bookingIndex);
        log.info("Created Booking: {}", booking);
        return booking;
    }

    @Override
    public Booking getBookingById(String bookingId) {
        return bookings.get(idToBookingIndex.get(bookingId));
    }

    @Override
    public Booking updateBooking(Booking booking) {
        bookings.set(idToBookingIndex.get(booking.getId()), booking);
        return bookings.get(idToBookingIndex.get(booking.getId()));
    }

    @Override
    public List<Booking> getByVehicleId(String vehicleId) {
        return vehicleIdToBookingsIndex.get(vehicleId).stream().map(bookingInd -> bookings.get(bookingInd)).collect(Collectors.toList());
    }
}
