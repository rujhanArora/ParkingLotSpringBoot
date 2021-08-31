package com.udaan.parkingLot.exceptions;

public class ParkingLotCapacityExceededException extends RuntimeException {
    public ParkingLotCapacityExceededException(String message) {
        super("Parking Lot capacity Exceeded ! " + message);
    }
}
