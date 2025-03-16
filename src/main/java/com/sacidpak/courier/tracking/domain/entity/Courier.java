package com.sacidpak.courier.tracking.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "courier",
        indexes = {@Index(name = "ix_courier_identityNumber", columnList = "identity_number")},
        uniqueConstraints = {@UniqueConstraint(name = "ix_unique_identityNumber", columnNames = "identity_number")})
public class Courier extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 8125339494607621408L;

    @NotBlank(message = "not.blank.message")
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotBlank(message = "not.blank.message")
    @Column(name = "identity_number", length = 11, nullable = false)
    private String identityNumber;
}
