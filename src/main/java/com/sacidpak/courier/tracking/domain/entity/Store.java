package com.sacidpak.courier.tracking.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "store",
        indexes = {
                @Index(name = "ix_store_latitude_longitude", columnList = "latitude,longitude"),
                @Index(name = "ix_store_latitude", columnList = "latitude"),
                @Index(name = "ix_store_longitude", columnList = "longitude")
        },
        uniqueConstraints = {@UniqueConstraint(name = "ix_unique_store_code", columnNames = "code")})
public class Store extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 3077315492850989990L;

    @NotBlank(message = "not.blank.message")
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @NotBlank(message = "not.blank.message")
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @NotNull(message = "not.null.message")
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull(message = "not.null.message")
    @Column(name = "longitude", nullable = false)
    private Double longitude;
}
