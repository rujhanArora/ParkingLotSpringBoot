package com.udaan.parkingLot.models.rateCard;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public interface RateCard {
    Double calculateRate(Date startDate, Date endDate);
}
