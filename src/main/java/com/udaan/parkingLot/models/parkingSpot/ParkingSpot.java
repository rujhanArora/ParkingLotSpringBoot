package com.udaan.parkingLot.models.parkingSpot;

import com.sun.org.apache.bcel.internal.generic.LUSHR;
import com.udaan.parkingLot.models.rateCard.RateCard;
import com.udaan.parkingLot.models.vehicle.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public abstract class ParkingSpot {
    private String id;
    private String floorId;
    private SpotStatus spotStatus;
    private ParkingSpotType parkingSpotType;
    private RateCard rateCard;
    private List<VehicleType> supportedVehiclesTypes;
}
