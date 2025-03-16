package com.sacidpak.courier.tracking.service.calculator;

public interface DistanceCalculatorService {
    Double calculateDistance(Double firstLatitude, Double firstLongitude, Double secondLatitude, Double secondLongitude);
}
