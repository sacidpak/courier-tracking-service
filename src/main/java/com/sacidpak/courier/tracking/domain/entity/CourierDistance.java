package com.sacidpak.courier.tracking.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "courier_distance",
       uniqueConstraints = {@UniqueConstraint(name = "ix_unique_courier_distance_courier_id", columnNames = "courier_id")})
public class CourierDistance extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1641352634917011476L;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "courier_id", referencedColumnName = "id", nullable = false)
    private Courier courier;

    @Builder.Default
    @Column(name = "total_distance", precision = 20, scale = 3)
    private BigDecimal totalDistance = BigDecimal.ZERO;
}
