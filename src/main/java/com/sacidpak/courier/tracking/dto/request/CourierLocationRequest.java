package com.sacidpak.courier.tracking.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierLocationRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -822598785798507605L;

    @NotNull(message = "not.null.message")
    private Long courierId;

    @NotNull(message = "not.null.message")
    private Double latitude;

    @NotNull(message = "not.null.message")
    private Double longitude;
}
