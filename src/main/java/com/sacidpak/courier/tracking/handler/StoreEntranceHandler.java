package com.sacidpak.courier.tracking.handler;

import com.sacidpak.courier.tracking.config.EnvironmentConfig;
import com.sacidpak.courier.tracking.dto.StoreDto;
import com.sacidpak.courier.tracking.dto.request.CourierLocationRequest;
import com.sacidpak.courier.tracking.service.CourierStoreEntranceService;
import com.sacidpak.courier.tracking.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.sacidpak.courier.tracking.config.async.AsyncConfiguration.DEFAULT_THREAD_EXECUTOR_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreEntranceHandler {

    private final StoreService storeService;

    private final CourierStoreEntranceService courierStoreEntranceService;

    private final EnvironmentConfig environmentConfig;

    @EventListener
    @Transactional
    @Async(DEFAULT_THREAD_EXECUTOR_NAME)
    public void handle(CourierLocationRequest event) {
        var nearestStore = findNearestStore(event);
        if (nearestStore == null) {
            return;
        }
        var courierId = event.getCourierId();
        courierStoreEntranceService.saveEntrance(courierId, nearestStore);
        log.info("Courier with id: {}, entranced to {}, distance to store is : {}m",
                courierId, nearestStore.getStoreName(), nearestStore.getDistanceToStore());
    }

    private StoreDto findNearestStore(CourierLocationRequest event) {
        var limitTime = Instant.now().minusSeconds(60);
        var radius = environmentConfig.getStoreDistanceRadius();
        return storeService.findNearestStoreWithinRadiusByTime(event, radius, limitTime);
    }
}
