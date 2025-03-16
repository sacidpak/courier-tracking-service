package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.domain.entity.Store;
import com.sacidpak.courier.tracking.domain.repository.StoreRepository;
import com.sacidpak.courier.tracking.dto.StoreDto;
import com.sacidpak.courier.tracking.dto.request.CourierLocationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    @Test
    public void shouldReturnNearestStore_whenFindNearestStoreWithinRadiusByTimeIsCalled() {
        var locationRequest = new CourierLocationRequest(1L, 40.9923307, 29.1244229);
        var radius = 100.0;
        var limitTime = Instant.now();
        var expectedStore = new StoreDto() {
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

        when(storeRepository.findNearestStoreWithinRadiusByTime(
                eq(locationRequest.getLatitude()), eq(locationRequest.getLongitude()),
                eq(radius), eq(locationRequest.getCourierId()), eq(limitTime)
        )).thenReturn(expectedStore);

        StoreDto result = storeService.findNearestStoreWithinRadiusByTime(locationRequest, radius, limitTime);

        assertNotNull(result);
        assertEquals(expectedStore.getId(), result.getId());
        assertEquals(expectedStore.getStoreName(), result.getStoreName());
    }

    @Test
    public void shouldReturnStore_whenGetReferenceByIdIsCalled() {
        var storeId = 1L;
        var expectedStore = new Store();
        expectedStore.setId(storeId);

        when(storeRepository.getReferenceById(storeId)).thenReturn(expectedStore);

        var result = storeService.getReferenceById(storeId);

        assertNotNull(result);
        assertEquals(storeId, result.getId());
    }
}
