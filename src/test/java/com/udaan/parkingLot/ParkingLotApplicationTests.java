package com.udaan.parkingLot;

import com.udaan.parkingLot.DTOs.CustomerDTO;
import com.udaan.parkingLot.models.*;
import com.udaan.parkingLot.models.parkingSpot.ParkingSpot;
import com.udaan.parkingLot.models.parkingSpot.ParkingSpotType;
import com.udaan.parkingLot.models.parkingSpot.SpotFindStrategy;
import com.udaan.parkingLot.models.parkingSpot.SpotStatus;
import com.udaan.parkingLot.models.vehicle.Vehicle;
import com.udaan.parkingLot.models.vehicle.VehicleType;
import com.udaan.parkingLot.services.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@Slf4j
class ParkingLotApplicationTests {

	@Autowired
	CustomerService customerService;
	@Autowired
	ParkingLotService parkingLotService;
	@Autowired
	SpotService spotService;
	@Autowired
	BookingService bookingService;
	@Autowired
	VehicleService vehicleService;
	@Autowired
	FloorService floorService;

	@Test
	void contextLoads() {
		ParkingLot parkingLot = parkingLotService.createParkingLot("Udaan Parking");
		Assertions.assertThat(parkingLot.getName().equals("Udaan Parking"));

		Map<ParkingSpotType, Integer> spotTypeToCapacityForFirstFloor = new HashMap<>();
		spotTypeToCapacityForFirstFloor.put(ParkingSpotType.FOUR_WHEELER, 1);
		spotTypeToCapacityForFirstFloor.put(ParkingSpotType.TWO_WHEELER, 1);
		ParkingLotFloor firstFloor = parkingLotService.addFloor(parkingLot.getId(), "Ground Floor", spotTypeToCapacityForFirstFloor);
		Assertions.assertThat(parkingLotService.getParkingLotById(parkingLot.getId()).getFloors().size() == 1);

		ParkingSpot carSpot1 = floorService.addSpot(ParkingSpotType.FOUR_WHEELER, firstFloor.getId());
		try {
			floorService.addSpot(ParkingSpotType.FOUR_WHEELER, firstFloor.getId());
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		Assertions.assertThat(carSpot1.getId().equals(spotService.getSpotById(carSpot1.getId())));

		ParkingSpot firstBookingCarSlot = spotService.getSpot(VehicleType.FOUR_WHEELER, SpotFindStrategy.RANDOM_EMPTY).get();
		Assertions.assertThat(carSpot1.getId().equals(firstBookingCarSlot.getId()));

//		ParkingSpot secondBookingCarSlot = spotService.getSpot(VehicleType.FOUR_WHEELER, SpotFindStrategy.RANDOM_EMPTY).get();
//		Assertions.assertThat(secondBookingCarSlot == null);

		Customer customer = customerService.addCustomer(CustomerDTO.builder().email("rj").name("Rj Arora").build());
		Vehicle carVehicle = vehicleService.addVehicle(Vehicle.builder().vehicleNo("HR15G5772").vehicleType(VehicleType.FOUR_WHEELER).customerId(customer.getId()).build());
		Assertions.assertThat(carVehicle.getId().equals(vehicleService.filterVehicles(VehicleFilter.builder().vehicleNo(carVehicle.getVehicleNo()).build()).get(0).getId()));

		Booking carBooking = bookingService.checkIn(customer.getId(), carVehicle.getId(), firstBookingCarSlot.getId());
		Assertions.assertThat(spotService.getSpotById(firstBookingCarSlot.getId()).getSpotStatus().equals(SpotStatus.BOOKED));

		Optional<ParkingSpot> secondEmptyCarSpot = spotService.getSpot(VehicleType.FOUR_WHEELER, SpotFindStrategy.RANDOM_EMPTY);
		Assertions.assertThat(!secondEmptyCarSpot.isPresent());

		try {
			bookingService.checkOut(carBooking.getId());
		} catch (Exception e) {
			log.info("e: " + e.getMessage());
		}
		bookingService.payForBooking(carBooking.getId());
		bookingService.checkOut(carBooking.getId());
		Assertions.assertThat(bookingService.getBookingById(carBooking.getId()).getStatus().equals(BookingStatus.CHECKED_OUT));

		Optional<ParkingSpot> emptiedSpot = spotService.getSpot(VehicleType.FOUR_WHEELER, SpotFindStrategy.RANDOM_EMPTY);
		Assertions.assertThat(emptiedSpot.isPresent());
		bookingService.checkIn(customer.getId(), carVehicle.getId(), emptiedSpot.get().getId());

		log.info("Bookings for {} => {}", carVehicle.getId(), bookingService.getBookingsByVehicleId(carVehicle.getId()));
	}

}
