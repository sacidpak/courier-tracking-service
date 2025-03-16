package com.sacidpak.courier.tracking.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courier_location",
       indexes = {@Index(name = "ix_courier_location_courier_id", columnList = "courier_id")})
public class CourierLocation extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7843697240961936297L;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "courier_id", referencedColumnName = "id", nullable = false)
    private Courier courier;

    @NotNull(message = "not.null.message")
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull(message = "not.null.message")
    @Column(name = "longitude", nullable = false)
    private Double longitude;
}
