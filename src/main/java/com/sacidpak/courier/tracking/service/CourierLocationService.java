package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.domain.entity.Courier;
import com.sacidpak.courier.tracking.domain.entity.CourierLocation;
import com.sacidpak.courier.tracking.domain.repository.CourierLocationRepository;
import com.sacidpak.courier.tracking.dto.request.CourierLocationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierLocationService {

    private final CourierLocationRepository courierLocationRepository;

    @Transactional
    public void save(Courier courier, CourierLocationRequest request) {
        var courierLocation = CourierLocation.builder()
                .courier(courier)
                .longitude(request.getLongitude())
                .latitude(request.getLatitude())
                .build();
        courierLocationRepository.save(courierLocation);
    }

    public Optional<CourierLocation> getLastLocation(Long courierId) {
        return courierLocationRepository.findTopByCourierIdOrderByCreatedAtDesc(courierId);
    }
}
