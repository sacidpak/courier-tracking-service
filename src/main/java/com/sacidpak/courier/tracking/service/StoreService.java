package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.domain.entity.Store;
import com.sacidpak.courier.tracking.domain.repository.StoreRepository;
import com.sacidpak.courier.tracking.dto.StoreDto;
import com.sacidpak.courier.tracking.dto.request.CourierLocationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreDto findNearestStoreWithinRadiusByTime(CourierLocationRequest request, Double radius, Instant limitTime) {
        return storeRepository.findNearestStoreWithinRadiusByTime(request.getLatitude(), request.getLongitude(),
                radius, request.getCourierId(), limitTime);
    }

    public Store getReferenceById(Long id) {
        return storeRepository.getReferenceById(id);
    }
}
