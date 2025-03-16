package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.config.EnvironmentConfig;
import com.sacidpak.courier.tracking.domain.entity.Courier;
import com.sacidpak.courier.tracking.domain.entity.CourierDistance;
import com.sacidpak.courier.tracking.domain.repository.CourierDistanceRepository;
import com.sacidpak.courier.tracking.domain.repository.CourierRepository;
import com.sacidpak.courier.tracking.dto.request.CourierLocationRequest;
import com.sacidpak.courier.tracking.enums.DistanceCalculateType;
import com.sacidpak.courier.tracking.service.calculator.DistanceCalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierDistanceService {

    private final CourierDistanceRepository courierDistanceRepository;

    private final CourierLocationService courierLocationService;

    private final Map<DistanceCalculateType, DistanceCalculatorService> distanceStrategyMap;

    private final EnvironmentConfig environmentConfig;

    @Transactional
    public void updateTotalDistance(Courier courier, CourierLocationRequest locationRequest) {
        var distance = 0.0;
        var courierId = locationRequest.getCourierId();
        var lastLocationOpt = courierLocationService.getLastLocation(courierId);

        if (lastLocationOpt.isPresent()) {
            var lastLocation = lastLocationOpt.get();
            distance = calculateDistance(lastLocation.getLatitude(), lastLocation.getLongitude(),
                    locationRequest.getLatitude(), locationRequest.getLongitude());
        }

        var courierDistance = courierDistanceRepository.findByCourierId(courierId).orElse(new CourierDistance());
        courierDistance.setCourier(courier);
        courierDistance.setTotalDistance(courierDistance.getTotalDistance().add(BigDecimal.valueOf(distance)));

        courierDistanceRepository.save(courierDistance);
    }

    private Double calculateDistance(Double firstLatitude, Double firstLongitude, Double secondLatitude, Double secondLongitude) {
        var strategy = distanceStrategyMap.get(environmentConfig.getDistanceStrategy());
        if (strategy == null) {
            log.error("Unknown distance calculate type: {}", environmentConfig.getDistanceStrategy());
            return 0.0;
        }

        return strategy.calculateDistance(firstLatitude, firstLongitude, secondLatitude, secondLongitude);
    }

    public Optional<CourierDistance> getByCourierId(Long courierId) {
        return courierDistanceRepository.findByCourierId(courierId);
    }
}
