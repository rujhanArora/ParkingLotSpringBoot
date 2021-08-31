package com.udaan.parkingLot.models.parkingSpot;

import com.udaan.parkingLot.models.rateCard.HourlyRateCard;
import com.udaan.parkingLot.models.rateCard.RateCard;
import com.udaan.parkingLot.models.vehicle.VehicleType;
import com.udaan.parkingLot.utils.TokenUtil;

import java.util.Collections;

public class FourWheelerSpot extends ParkingSpot {
    public FourWheelerSpot(String floorId) {
        super(TokenUtil.generateRandomTokenDefaultLength(), floorId, SpotStatus.FREE, ParkingSpotType.FOUR_WHEELER, new HourlyRateCard(20.0), Collections.singletonList(VehicleType.FOUR_WHEELER));
    }
}