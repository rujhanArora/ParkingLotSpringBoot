package com.udaan.parkingLot.models.vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Vehicle {
    private String id;
    private String customerId;
    private String vehicleNo;
    private VehicleType vehicleType;
    private VehicleSubType vehicleSubType;
}
