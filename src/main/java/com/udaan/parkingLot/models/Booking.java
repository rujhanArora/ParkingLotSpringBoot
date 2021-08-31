package com.udaan.parkingLot.models;

import com.udaan.parkingLot.utils.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Booking {
    private String id;
    private String customerId;
    // Vehicle id because customer can bring different vehicles at different times
    private String vehicleId;
    private Date checkInAt;
    private String parkingSpotId;
    private BookingStatus status;
    private Double amountPaid;
    private Date checkedOutAt;

    public Booking(String customerId, String vehicleId, String parkingSpotId) {
        this.setId(TokenUtil.generateRandomTokenDefaultLength());
        this.setCustomerId(customerId);
        this.setVehicleId(vehicleId);
        this.setParkingSpotId(parkingSpotId);
        this.setCheckInAt(new Date());
        this.setStatus(BookingStatus.ACTIVE);
    }
}
