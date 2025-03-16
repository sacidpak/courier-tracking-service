package com.sacidpak.courier.tracking.handler;

import com.sacidpak.courier.tracking.config.EnvironmentConfig;
import com.sacidpak.courier.tracking.dto.StoreDto;
import com.sacidpak.courier.tracking.dto.request.CourierLocationRequest;
import com.sacidpak.courier.tracking.service.CourierStoreEntranceService;
import com.sacidpak.courier.tracking.service.StoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreEntranceHandlerTest {

    @Mock
    private StoreService storeService;

    @Mock
    private CourierStoreEntranceService courierStoreEntranceService;

    @Mock
    private EnvironmentConfig environmentConfig;

    @InjectMocks
    private StoreEntranceHandler storeEntranceHandler;

    @Test
    public void shouldHandleStoreEntrance_whenNearestStoreExists() {
        // Given
        var courierId = 1L;
        var nearestStore = new StoreDto() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public Double getDistanceToStore() {
                return 1210.5434;
            }

            @Override
            public String getStoreName() {
                return "Ataşehir";
            }
        };

        var locationRequest = new CourierLocationRequest();
        locationRequest.setCourierId(courierId);
        locationRequest.setLatitude(40.0);
        locationRequest.setLongitude(29.0);

        when(environmentConfig.getStoreDistanceRadius()).thenReturn(100d);
        when(storeService.findNearestStoreWithinRadiusByTime(eq(locationRequest), eq(100d), any(Instant.class)))
                .thenReturn(nearestStore);

        // When
        storeEntranceHandler.handle(locationRequest);

        // Then
        verify(courierStoreEntranceService).saveEntrance(eq(courierId), eq(nearestStore));
        verifyNoMoreInteractions(courierStoreEntranceService);
    }

    @Test
    public void shouldNotHandleStoreEntrance_whenNoNearestStoreFound() {
        // Given
        var courierId = 1L;
        var locationRequest = new CourierLocationRequest();
        locationRequest.setCourierId(courierId);
        locationRequest.setLatitude(40.0);
        locationRequest.setLongitude(29.0);

        when(environmentConfig.getStoreDistanceRadius()).thenReturn(100d);
        when(storeService.findNearestStoreWithinRadiusByTime(eq(locationRequest), eq(100d), any(Instant.class)))
                .thenReturn(null);

        // When
        storeEntranceHandler.handle(locationRequest);

        // Then
        verify(courierStoreEntranceService, never()).saveEntrance(anyLong(), any(StoreDto.class));
    }
}
