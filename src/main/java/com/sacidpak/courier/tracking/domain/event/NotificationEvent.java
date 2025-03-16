package com.sacidpak.courier.tracking.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = -825598785798507605L;

    private Long storeId;

    private Long courierId;
}
