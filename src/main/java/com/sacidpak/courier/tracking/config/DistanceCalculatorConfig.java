package com.sacidpak.courier.tracking.config;

import com.sacidpak.courier.tracking.enums.DistanceCalculateType;
import com.sacidpak.courier.tracking.service.calculator.DistanceCalculatorService;
import com.sacidpak.courier.tracking.service.calculator.GoogleDistanceCalculatorService;
import com.sacidpak.courier.tracking.service.calculator.OfflineDistanceCalculatorService;
import com.sacidpak.courier.tracking.service.calculator.ValhallaDistanceCalculatorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DistanceCalculatorConfig {

    @Bean
    public Map<DistanceCalculateType, DistanceCalculatorService> distanceStrategyMap() {
        var map = new HashMap<DistanceCalculateType, DistanceCalculatorService>();
        map.put(DistanceCalculateType.OFFLINE, new OfflineDistanceCalculatorService());
        map.put(DistanceCalculateType.VALHALLA, new ValhallaDistanceCalculatorService());
        map.put(DistanceCalculateType.GOOGLE, new GoogleDistanceCalculatorService());

        return map;
    }

}
