package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.config.EnvironmentConfig;
import com.sacidpak.courier.tracking.domain.entity.Courier;
import com.sacidpak.courier.tracking.domain.entity.CourierStoreEntrance;
import com.sacidpak.courier.tracking.domain.entity.Store;
import com.sacidpak.courier.tracking.domain.repository.CourierRepository;
import com.sacidpak.courier.tracking.domain.repository.CourierStoreEntranceRepository;
import com.sacidpak.courier.tracking.dto.StoreDto;
import com.sacidpak.courier.tracking.dto.request.CourierLocationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourierStoreEntranceTest {

    @Mock
    private StoreService storeService;

    @Mock
    private CourierStoreEntranceRepository courierStoreEntranceRepository;

    @Mock
    private EnvironmentConfig environmentConfig;

    @InjectMocks
    private CourierStoreEntranceService courierStoreEntranceService;

    @Test
    public void shouldHandleStoreEntrance_whenNearestStoreIsFound() {
        var locationRequest = new CourierLocationRequest(1L, 40.9923307, 29.1244229);
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
                return "MM";
            }
        };
        var courierId = 1L;
        var courier = new Courier();
        courier.setId(courierId);

        when(storeService.findNearestStoreWithinRadiusByTime(eq(locationRequest), anyDouble(), any())).thenReturn(nearestStore);
        when(storeService.getReferenceById(nearestStore.getId())).thenReturn(new Store());

        courierStoreEntranceService.storeEntranceProcess(courier, locationRequest);

        verify(storeService, times(1)).findNearestStoreWithinRadiusByTime(eq(locationRequest), anyDouble(), any());
        verify(courierStoreEntranceRepository, times(1)).save(any(CourierStoreEntrance.class));
    }

    @Test
    public void shouldNotProcessEntrance_whenNoNearestStoreIsFound() {
        var courierId = 1L;
        var courier = new Courier();
        courier.setId(courierId);
        var locationRequest = new CourierLocationRequest(1L, 40.9923307, 29.1244229);

        when(storeService.findNearestStoreWithinRadiusByTime(eq(locationRequest), anyDouble(), any())).thenReturn(null);

        courierStoreEntranceService.storeEntranceProcess(courier, locationRequest);

        verify(courierStoreEntranceRepository, times(0)).save(any(CourierStoreEntrance.class));
    }

}
