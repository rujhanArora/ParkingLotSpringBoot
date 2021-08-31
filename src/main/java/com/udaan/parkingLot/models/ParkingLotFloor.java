package com.udaan.parkingLot.models;

import com.udaan.parkingLot.exceptions.ParkingFloorCapacityExceededException;
import com.udaan.parkingLot.models.parkingSpot.ParkingSpot;
import com.udaan.parkingLot.models.parkingSpot.ParkingSpotType;
import com.udaan.parkingLot.utils.TokenUtil;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ParkingLotFloor {
    private String id;
    private String name;
    private String parkingLotId;
    private Map<ParkingSpotType, Integer> spotTypeToCapacityMap = new HashMap<>();
    @Getter
    private Map<ParkingSpotType, List<ParkingSpot>> spotTypeToSpotsMap = new HashMap<>();

    public ParkingLotFloor(String name, String parkingLotId, Map<ParkingSpotType, Integer> spotTypeToCapacityMap) {
        this.id = TokenUtil.generateRandomTokenDefaultLength();
        this.name = name;
        this.parkingLotId = parkingLotId;
        this.spotTypeToCapacityMap = spotTypeToCapacityMap;
    }

    public void addSpot(ParkingSpot spot) {
        ParkingSpotType parkingSpotType = spot.getParkingSpotType();
        if (!spotTypeToCapacityMap.containsKey(parkingSpotType)) {
            spotTypeToCapacityMap.put(parkingSpotType, 0);
        }
        if (!spotTypeToSpotsMap.containsKey(parkingSpotType)) {
            spotTypeToSpotsMap.put(parkingSpotType, new ArrayList<>());
        }
        if (spotTypeToCapacityMap.get(spot.getParkingSpotType()) <= spotTypeToSpotsMap.get(spot.getParkingSpotType()).size()) {
            throw new ParkingFloorCapacityExceededException("Capacity Exceeded");
        }
        spotTypeToSpotsMap.get(parkingSpotType).add(spot);
    }

    public boolean canAddSpot(ParkingSpotType parkingSpotType) {
        if (!spotTypeToCapacityMap.containsKey(parkingSpotType)) {
            spotTypeToCapacityMap.put(parkingSpotType, 0);
        }
        if (!spotTypeToSpotsMap.containsKey(parkingSpotType)) {
            spotTypeToSpotsMap.put(parkingSpotType, new ArrayList<>());
        }
        return spotTypeToCapacityMap.get(parkingSpotType) > spotTypeToSpotsMap.get(parkingSpotType).size();
    }
}
