package com.udaan.parkingLot.models.parkingSpot;

import com.udaan.parkingLot.models.rateCard.HourlyRateCard;
import com.udaan.parkingLot.models.rateCard.RateCard;
import com.udaan.parkingLot.models.vehicle.VehicleType;
import com.udaan.parkingLot.utils.TokenUtil;

import java.util.Collections;

public class TwoWheelerSpot extends ParkingSpot {
    public TwoWheelerSpot(String floorId) {
        super(TokenUtil.generateRandomTokenDefaultLength(), floorId, SpotStatus.FREE, ParkingSpotType.TWO_WHEELER, new HourlyRateCard(10.0), Collections.singletonList(VehicleType.TWO_WHEELER));
    }
}
