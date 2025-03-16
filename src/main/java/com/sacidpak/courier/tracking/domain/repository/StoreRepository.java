package com.sacidpak.courier.tracking.domain.repository;

import com.sacidpak.courier.tracking.domain.entity.Store;
import com.sacidpak.courier.tracking.dto.StoreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> getByCode(String code);

    @Query(value = """
            select 
                s.id as id,
                ST_DistanceSphere(ST_MakePoint(:longitude, :latitude), ST_MakePoint(s.longitude, s.latitude)) AS distanceToStore,
                s.name as storeName
            from store s 
            where s.id not in (select cse.store_id
                               from courier_store_entrance cse
                               where cse.courier_id = :courierId and cse.created_at >= :limitTime
                               group by cse.store_id)
                and ST_DistanceSphere(ST_MakePoint(:longitude, :latitude), ST_MakePoint(s.longitude, s.latitude)) <= :radius 
            order by distanceToStore desc limit 1
            """, nativeQuery = true)
    StoreDto findNearestStoreWithinRadiusByTime(@Param("latitude") Double courierLatitude,
                                                @Param("longitude") Double courierLongitude,
                                                @Param("radius") Double radius,
                                                @Param("courierId") Long courierId,
                                                @Param("limitTime") Instant limitTime);

}
