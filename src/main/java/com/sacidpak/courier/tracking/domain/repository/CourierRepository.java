package com.sacidpak.courier.tracking.domain.repository;

import com.sacidpak.courier.tracking.domain.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    Optional<Courier> findCourierByIdentityNumber(String identityNumber);
}
