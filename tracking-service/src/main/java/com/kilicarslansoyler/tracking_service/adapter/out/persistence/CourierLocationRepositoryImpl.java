package com.kilicarslansoyler.tracking_service.adapter.out.persistence;

import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import com.kilicarslansoyler.tracking_service.domain.repository.CourierLocationRepository;
import com.kilicarslansoyler.tracking_service.infrastructure.persistence.entity.CourierLocationJpaEntity;
import com.kilicarslansoyler.tracking_service.infrastructure.persistence.mapper.CourierLocationMapper;
import com.kilicarslansoyler.tracking_service.infrastructure.persistence.repository.CourierLocationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourierLocationRepositoryImpl implements CourierLocationRepository {

    private final CourierLocationJpaRepository jpaRepository;
    private final CourierLocationMapper mapper;

    @Override
    public CourierLocation save(CourierLocation location) {
        CourierLocationJpaEntity entity = mapper.toEntity(location);
        CourierLocationJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<CourierLocation> findByCourierId(Long courierId) {
        return jpaRepository.findByCourierId(courierId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<CourierLocation> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
