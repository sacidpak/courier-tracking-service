package com.sacidpak.courier.tracking.service;

import com.sacidpak.courier.tracking.domain.entity.Courier;
import com.sacidpak.courier.tracking.domain.repository.CourierRepository;
import com.sacidpak.courier.tracking.dto.request.CourierLocationRequest;
import com.sacidpak.courier.tracking.dto.response.CourierTotalDistanceResponse;
import com.sacidpak.courier.tracking.exception.NotFoundException;
import com.sacidpak.courier.tracking.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;

    private final CourierLocationService courierLocationService;

    private final CourierStoreEntranceService courierStoreEntranceService;

    private final CourierDistanceService courierDistanceService;

    @Transactional
    public void processLocation(CourierLocationRequest locationRequest) {
        var courier = courierRepository.findById(locationRequest.getCourierId())
                .orElseThrow(() -> new NotFoundException(MessageUtil.getMessage("courier.not.found")));

        courierDistanceService.updateTotalDistance(courier, locationRequest);
        courierLocationService.save(courier, locationRequest);
        courierStoreEntranceService.storeEntranceProcess(courier, locationRequest);
    }

    public CourierTotalDistanceResponse getTotalTravelDistance(Long courierId) {
        return courierDistanceService.getByCourierId(courierId)
                .map(courierDistance -> CourierTotalDistanceResponse.builder()
                        .totalDistance(courierDistance.getTotalDistance())
                        .courierId(courierId)
                        .build())
                .orElseThrow(() -> new NotFoundException(MessageUtil.getMessage("courier.distance.not.found")));
    }

    public Courier getReferenceById(Long id) {
        return courierRepository.getReferenceById(id);
    }
}
