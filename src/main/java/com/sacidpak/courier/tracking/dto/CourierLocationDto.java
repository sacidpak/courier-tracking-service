package com.sacidpak.courier.tracking.dto;

import java.time.Instant;

public record CourierLocationDto(Instant time,
                                 Double latitude,
                                 Double longitude) {

}
