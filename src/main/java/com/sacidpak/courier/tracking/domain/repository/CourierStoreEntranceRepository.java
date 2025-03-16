package com.sacidpak.courier.tracking.domain.repository;

import com.sacidpak.courier.tracking.domain.entity.CourierStoreEntrance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierStoreEntranceRepository extends JpaRepository<CourierStoreEntrance, Long> {
}
