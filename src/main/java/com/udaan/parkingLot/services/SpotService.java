package com.udaan.parkingLot.services;

import com.udaan.parkingLot.exceptions.InvalidParkingSpotException;
import com.udaan.parkingLot.models.vehicle.VehicleType;
import com.udaan.parkingLot.models.parkingSpot.*;
import com.udaan.parkingLot.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class SpotService {
    List<ParkingSpot> parkingSpotList = new ArrayList<>();
    private Map<String, Integer> idToSpotsIndex = new HashMap<>();
    private Map<String, List<Integer>> floorIdToSpotsIndex = new HashMap<>();
    private Map<ParkingSpotType, List<Integer>> spotTypeToSpotsIndex = new HashMap<>();

    public ParkingSpot addSpot(ParkingSpotType spotType, String floorId) {
        ParkingSpot addedSpot;
        switch (spotType) {
            case TWO_WHEELER:
                addedSpot = new TwoWheelerSpot(floorId);
                break;
            case FOUR_WHEELER:
                addedSpot = new FourWheelerSpot(floorId);
                break;
            default:
                throw new InvalidParkingSpotException(spotType + " floorId: " + floorId);
        }
        parkingSpotList.add(addedSpot);
        int addedSpotIndex = parkingSpotList.size() - 1;
        floorIdToSpotsIndex.computeIfAbsent(floorId, spots -> new ArrayList<>())
                .add(addedSpotIndex);
        spotTypeToSpotsIndex.computeIfAbsent(spotType, spots -> new ArrayList<>())
                .add(addedSpotIndex);
        idToSpotsIndex.put(addedSpot.getId(), addedSpotIndex);
        return addedSpot;
    }

    private Optional<ParkingSpot> getRandomEmptySpot(VehicleType vehicleType) {
        for (ParkingSpot parkingSpot : parkingSpotList) {
            if (parkingSpot.getSpotStatus().equals(SpotStatus.FREE) &&
            parkingSpot.getSupportedVehiclesTypes().contains(vehicleType)) {
                return Optional.of(parkingSpot);
            }
        }
        return Optional.empty();
    }

    public ParkingSpot getSpotById(String spotId) {
        return parkingSpotList.get(idToSpotsIndex.get(spotId));
    }

    // Synchronized to handle concurrency
    public void bookSpot(String spotId) {
        ParkingSpot parkingSpot = getSpotById(spotId);
        ValidationUtil.ensureTrue(parkingSpot.getSpotStatus().equals(SpotStatus.FREE), "SLot with id: " + spotId + " not free!");
        synchronized (SpotService.class) {
            ValidationUtil.ensureTrue(parkingSpot.getSpotStatus().equals(SpotStatus.FREE), "SLot with id: " + spotId + " not free!");
            parkingSpot.setSpotStatus(SpotStatus.BOOKED);
        }
    }

    public void vacateSpot(String spotId) {
        ParkingSpot parkingSpot = getSpotById(spotId);
        parkingSpot.setSpotStatus(SpotStatus.FREE);
    }

    public Optional<ParkingSpot> getSpot(VehicleType vehicleType, SpotFindStrategy spotFindStrategy) {
        switch (spotFindStrategy) {
            default:
                return getRandomEmptySpot(vehicleType);
        }
    }
}
