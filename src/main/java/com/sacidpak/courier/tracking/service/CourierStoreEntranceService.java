package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.config.EnvironmentConfig;
import com.sacidpak.courier.tracking.domain.entity.Courier;
import com.sacidpak.courier.tracking.domain.entity.CourierStoreEntrance;
import com.sacidpak.courier.tracking.domain.repository.CourierRepository;
import com.sacidpak.courier.tracking.domain.repository.CourierStoreEntranceRepository;
import com.sacidpak.courier.tracking.dto.StoreDto;
import com.sacidpak.courier.tracking.dto.request.CourierLocationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.sacidpak.courier.tracking.config.async.AsyncConfiguration.DEFAULT_THREAD_EXECUTOR_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierStoreEntranceService {

    private final StoreService storeService;

    private final CourierStoreEntranceRepository courierStoreEntranceRepository;

    private final EnvironmentConfig environmentConfig;

    @Transactional
    @Async(DEFAULT_THREAD_EXECUTOR_NAME)
    public void storeEntranceProcess(Courier courier, CourierLocationRequest request) {
        var nearestStore = findNearestStore(request);
        if (nearestStore == null) {
            return;
        }
        saveEntrance(courier, nearestStore);
        log.info("Courier with id: {}, entranced to {}, distance to store is : {}m",
                request.getCourierId(), nearestStore.getStoreName(), nearestStore.getDistanceToStore());
    }

    private StoreDto findNearestStore(CourierLocationRequest request) {
        var limitTime = Instant.now().minusSeconds(60);
        var radius = environmentConfig.getStoreDistanceRadius();
        return storeService.findNearestStoreWithinRadiusByTime(request, radius, limitTime);
    }

    private void saveEntrance(Courier courier, StoreDto nearestStore) {
        var store = storeService.getReferenceById(nearestStore.getId());
        var courierStoreEntrance = CourierStoreEntrance.builder()
                .store(store)
                .courier(courier)
                .build();
        courierStoreEntranceRepository.save(courierStoreEntrance);
    }

}
