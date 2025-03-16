package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.domain.entity.Courier;
import com.sacidpak.courier.tracking.domain.entity.CourierStoreEntrance;
import com.sacidpak.courier.tracking.domain.entity.Store;
import com.sacidpak.courier.tracking.domain.repository.CourierStoreEntranceRepository;
import com.sacidpak.courier.tracking.dto.StoreDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourierStoreEntranceTest {

    @Mock
    private CourierStoreEntranceRepository courierStoreEntranceRepository;

    @Mock
    private CourierService courierService;

    @Mock
    private StoreService storeService;

    @InjectMocks
    private CourierStoreEntranceService courierStoreEntranceService;

    @Test
    public void shouldSaveCourierStoreEntrance_whenSaveIsCalled() {
        var storeDto = new StoreDto() {
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

        when(storeService.getReferenceById(storeDto.getId())).thenReturn(new Store());
        when(courierService.getReferenceById(courierId)).thenReturn(new Courier());

        courierStoreEntranceService.saveEntrance(courierId, storeDto);

        verify(courierStoreEntranceRepository, times(1)).save(any(CourierStoreEntrance.class));
    }

}
