package com.udaan.parkingLot.services;

import com.udaan.parkingLot.exceptions.ParkingLotCapacityExceededException;
import com.udaan.parkingLot.models.ParkingLot;
import com.udaan.parkingLot.models.ParkingLotFloor;
import com.udaan.parkingLot.models.parkingSpot.ParkingSpotType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ParkingLotService {
    @Autowired FloorService floorService;

    List<ParkingLot> parkingLots = new ArrayList<>();
    Map<String, Integer> idToParkingLotInd = new HashMap<>();

    public ParkingLot createParkingLot(String name) {
        ParkingLot parkingLot = new ParkingLot(name);
        parkingLots.add(parkingLot);
        idToParkingLotInd.put(parkingLot.getId(), parkingLots.size() - 1);
        log.info("added parkingLot: {}", parkingLot);
        return parkingLot;
    }

    public ParkingLot getParkingLotById(String parkingLotId) {
        return parkingLots.get(idToParkingLotInd.get(parkingLotId));
    }
    // Can use RBAC
    public ParkingLotFloor addFloor(String parkingLotId, String floorName, Map<ParkingSpotType, Integer> spotTypeToCapacityMap) {
        ParkingLot parkingLot = getParkingLotById(parkingLotId);
        ParkingLotFloor addedFloor = floorService.addFloor(parkingLotId, floorName, spotTypeToCapacityMap);
        parkingLot.addFloor(addedFloor);
        return addedFloor;
    }
}
