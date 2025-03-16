package com.sacidpak.courier.tracking.config.async;

import lombok.Data;

@Data
public class ThreadExecutorProperties {

    private String name;

    private int corePoolSize;

    private int maxPoolSize;

    private int queueCapacity;
}
