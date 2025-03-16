package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.domain.entity.CourierStoreEntrance;
import com.sacidpak.courier.tracking.domain.repository.CourierStoreEntranceRepository;
import com.sacidpak.courier.tracking.dto.StoreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierStoreEntranceService {

    private final StoreService storeService;

    private final CourierService courierService;

    private final CourierStoreEntranceRepository courierStoreEntranceRepository;

    public void saveEntrance(Long courierId, StoreDto nearestStore) {
        var store = storeService.getReferenceById(nearestStore.getId());
        var courier = courierService.getReferenceById(courierId);
        var courierStoreEntrance = CourierStoreEntrance.builder()
                .store(store)
                .courier(courier)
                .build();
        courierStoreEntranceRepository.save(courierStoreEntrance);
    }
}
