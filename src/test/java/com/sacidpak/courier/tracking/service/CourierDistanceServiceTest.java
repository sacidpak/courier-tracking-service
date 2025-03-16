package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.config.EnvironmentConfig;
import com.sacidpak.courier.tracking.domain.entity.Courier;
import com.sacidpak.courier.tracking.domain.entity.CourierDistance;
import com.sacidpak.courier.tracking.domain.entity.CourierLocation;
import com.sacidpak.courier.tracking.domain.repository.CourierDistanceRepository;
import com.sacidpak.courier.tracking.dto.request.CourierLocationRequest;
import com.sacidpak.courier.tracking.enums.DistanceCalculateType;
import com.sacidpak.courier.tracking.service.calculator.DistanceCalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourierDistanceServiceTest {

    @InjectMocks
    private CourierDistanceService courierDistanceService;

    @Mock
    private CourierDistanceRepository courierDistanceRepository;

    @Mock
    private CourierLocationService courierLocationService;

    @Mock
    private Map<DistanceCalculateType, DistanceCalculatorService> distanceStrategyMap;

    @Mock
    private EnvironmentConfig environmentConfig;

    @Mock
    private DistanceCalculatorService distanceCalculatorService;

    @Test
    public void shouldUpdateTotalDistance_whenLastLocationExists() {
        // Given
        var courierId = 1L;
        var courier = new Courier();
        courier.setId(courierId);
        CourierLocation lastLocation = new CourierLocation();
        lastLocation.setLatitude(39.0);
        lastLocation.setLongitude(28.0);


        var locationRequest = new CourierLocationRequest();
        locationRequest.setCourierId(1L);
        locationRequest.setLatitude(40.0);
        locationRequest.setLongitude(29.0);

        var existingCourierDistance = new CourierDistance();
        existingCourierDistance.setCourier(courier);
        existingCourierDistance.setTotalDistance(BigDecimal.ZERO);

        when(courierLocationService.getLastLocation(eq(courierId))).thenReturn(Optional.of(lastLocation));
        when(courierDistanceRepository.findByCourierId(courierId)).thenReturn(Optional.of(existingCourierDistance));
        when(environmentConfig.getDistanceStrategy()).thenReturn(DistanceCalculateType.OFFLINE);
        when(distanceStrategyMap.get(DistanceCalculateType.OFFLINE)).thenReturn(distanceCalculatorService);
        when(distanceCalculatorService.calculateDistance(anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(100.0);

        // When
        courierDistanceService.updateTotalDistance(courier, locationRequest);

        // Then
        verify(courierDistanceRepository).save(existingCourierDistance);
        assertEquals(BigDecimal.valueOf(100.0), existingCourierDistance.getTotalDistance());
    }

    @Test
    public void shouldUpdateDistanceIsZero_whenLastLocationDoesNotExist() {
        // Given
        var courierId = 1L;
        var courier = new Courier();
        courier.setId(courierId);

        var locationRequest = new CourierLocationRequest();
        locationRequest.setCourierId(1L);
        locationRequest.setLatitude(40.0);
        locationRequest.setLongitude(29.0);

        when(courierLocationService.getLastLocation(courierId)).thenReturn(Optional.empty());
        when(courierDistanceRepository.findByCourierId(courierId)).thenReturn(Optional.empty());

        // When
        courierDistanceService.updateTotalDistance(courier, locationRequest);

        // Then
        verify(courierDistanceRepository).save(any());
    }

    @Test
    public void shouldReturnCourierDistance_whenGetByCourierIdIsCalled() {
        // Given
        var courierId = 1L;
        var courier = new Courier();
        courier.setId(courierId);
        CourierDistance courierDistance = new CourierDistance();
        courierDistance.setCourier(courier);
        courierDistance.setTotalDistance(BigDecimal.ZERO);

        // When
        when(courierDistanceRepository.findByCourierId(courierId)).thenReturn(Optional.of(courierDistance));
        Optional<CourierDistance> result = courierDistanceService.getByCourierId(courierId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(courierId, result.get().getCourier().getId());
        assertEquals(BigDecimal.ZERO, result.get().getTotalDistance());
    }
}
