package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.domain.entity.Courier;
import com.sacidpak.courier.tracking.domain.entity.CourierLocation;
import com.sacidpak.courier.tracking.domain.repository.CourierLocationRepository;
import com.sacidpak.courier.tracking.dto.request.CourierLocationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourierLocationServiceTest {

    @Mock
    private CourierLocationRepository courierLocationRepository;

    @InjectMocks
    private CourierLocationService courierLocationService;

    @Test
    public void shouldSaveCourierLocation_whenSaveIsCalled() {
        var courierId = 1L;
        var courier = new Courier();
        courier.setId(courierId);
        var locationRequest = new CourierLocationRequest(1L, 40.9923307, 29.1244229);

        courierLocationService.save(courier, locationRequest);

        verify(courierLocationRepository, times(1)).save(any(CourierLocation.class));
    }

    @Test
    public void shouldReturnLastLocation_whenGetLastLocationIsCalled() {
        var courierId = 1L;
        var courier = new Courier();
        courier.setId(courierId);
        var courierLocation = CourierLocation.builder()
                .courier(courier)
                .latitude(40.9923307)
                .longitude(29.1244229)
                .build();

        when(courierLocationRepository.findTopByCourierIdOrderByCreatedAtDesc(1L))
                .thenReturn(Optional.of(courierLocation));

        Optional<CourierLocation> lastLocation = courierLocationService.getLastLocation(1L);

        assertTrue(lastLocation.isPresent());
        assertEquals(courierLocation, lastLocation.get());
    }

    @Test
    public void shouldReturnEmpty_whenNoLastLocationIsFound() {
        var courierId = 1L;
        var courier = new Courier();
        courier.setId(courierId);

        when(courierLocationRepository.findTopByCourierIdOrderByCreatedAtDesc(1L))
                .thenReturn(Optional.empty());

        Optional<CourierLocation> lastLocation = courierLocationService.getLastLocation(1L);

        assertFalse(lastLocation.isPresent());
    }
}
