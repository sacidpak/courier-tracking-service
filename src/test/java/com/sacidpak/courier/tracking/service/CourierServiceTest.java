package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.domain.entity.Courier;
import com.sacidpak.courier.tracking.domain.entity.CourierDistance;
import com.sacidpak.courier.tracking.domain.repository.CourierRepository;
import com.sacidpak.courier.tracking.dto.request.CourierLocationRequest;
import com.sacidpak.courier.tracking.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourierServiceTest {

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private CourierLocationService courierLocationService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private CourierDistanceService courierDistanceService;

    @InjectMocks
    private CourierService courierService;

    @Test
    public void shouldProcessLocation_whenValidRequestIsGiven() {
        CourierLocationRequest locationRequest = new CourierLocationRequest(1L, 40.9923307, 29.1244229);
        var courierId = 1L;
        var courier = new Courier();
        courier.setId(courierId);

        when(courierRepository.findById(locationRequest.getCourierId())).thenReturn(Optional.of(courier));

        courierService.processLocation(locationRequest);

        verify(courierDistanceService, times(1)).updateTotalDistance(courier, locationRequest);
        verify(courierLocationService, times(1)).save(courier, locationRequest);
        verify(applicationEventPublisher, times(1)).publishEvent(locationRequest);
    }

    @Test
    public void shouldThrowNotFoundException_whenCourierNotFound() {
        CourierLocationRequest locationRequest = new CourierLocationRequest(1L, 40.9923307, 29.1244229);

        when(courierRepository.findById(locationRequest.getCourierId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> courierService.processLocation(locationRequest));
    }

    @Test
    public void shouldReturnCourierTotalDistance_whenValidCourierIdIsGiven() {
        var courierId = 1L;
        var courier = new Courier();
        courier.setId(courierId);
        CourierDistance courierDistance = CourierDistance.builder()
                .totalDistance(BigDecimal.valueOf(100))
                .build();

        when(courierDistanceService.getByCourierId(courierId)).thenReturn(Optional.of(courierDistance));

        var response = courierService.getTotalTravelDistance(courierId);

        assertNotNull(response);
        assertEquals(courierId, response.getCourierId());
        assertEquals(BigDecimal.valueOf(100), response.getTotalDistance());
    }

    @Test
    public void shouldThrowNotFoundException_whenCourierDistanceNotFound() {
        var courierId = 1L;
        var courier = new Courier();
        courier.setId(courierId);

        when(courierDistanceService.getByCourierId(courierId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> courierService.getTotalTravelDistance(courierId));
    }

    @Test
    public void shouldReturnCourierReference_whenValidIdIsGiven() {
        var courierId = 1L;
        var courier = new Courier();
        courier.setId(courierId);

        when(courierRepository.getReferenceById(courierId)).thenReturn(courier);

        var result = courierService.getReferenceById(courierId);

        assertNotNull(result);
        assertEquals(courierId, result.getId());
    }
}
