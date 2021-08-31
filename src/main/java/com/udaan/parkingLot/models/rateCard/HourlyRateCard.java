package com.udaan.parkingLot.models.rateCard;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class HourlyRateCard implements RateCard {
    private Double hourlyRate;

    @Override
    public Double calculateRate(Date startDate, Date endDate) {
        final int MILLI_TO_HOUR = 1000 * 60 * 60;
//        return ((int)(endDate.getTime() - startDate.getTime()) / MILLI_TO_HOUR) * hourlyRate;
        return 20 * hourlyRate;
    }
}
