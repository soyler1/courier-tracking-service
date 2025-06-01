package com.kilicarslansoyler.tracking_service.infrastructure.persistence.repository;

import com.kilicarslansoyler.tracking_service.infrastructure.persistence.entity.CourierLocationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierLocationJpaRepository extends JpaRepository<CourierLocationJpaEntity, Long> {
    List<CourierLocationJpaEntity> findByCourierId(Long courierId);
}
