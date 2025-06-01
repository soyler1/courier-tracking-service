package com.kilicarslansoyler.tracking_service.adapter.out.persistence;

import com.kilicarslansoyler.tracking_service.domain.model.CourierLocation;
import com.kilicarslansoyler.tracking_service.infrastructure.persistence.entity.CourierLocationJpaEntity;
import com.kilicarslansoyler.tracking_service.infrastructure.persistence.mapper.CourierLocationMapper;
import com.kilicarslansoyler.tracking_service.infrastructure.persistence.repository.CourierLocationJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CourierLocationRepositoryImplTest {

    private CourierLocationJpaRepository jpaRepository;
    private CourierLocationMapper mapper;
    private CourierLocationRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(CourierLocationJpaRepository.class);
        mapper = mock(CourierLocationMapper.class);
        repository = new CourierLocationRepositoryImpl(jpaRepository, mapper);
    }

    @Test
    void shouldSaveCourierLocation() {
        // given
        CourierLocation domain = CourierLocation.builder()
                .id(null)
                .courierId(1L)
                .latitude(40.0)
                .longitude(29.0)
                .timestamp(LocalDateTime.now())
                .build();

        CourierLocationJpaEntity entity = mock(CourierLocationJpaEntity.class);
        CourierLocationJpaEntity savedEntity = mock(CourierLocationJpaEntity.class);
        CourierLocation mappedDomain = domain.builder().id(100L).build();

        when(mapper.toEntity(domain)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.toDomain(savedEntity)).thenReturn(mappedDomain);

        // when
        CourierLocation result = repository.save(domain);

        // then
        assertThat(result).isEqualTo(mappedDomain);
        verify(mapper).toEntity(domain);
        verify(jpaRepository).save(entity);
        verify(mapper).toDomain(savedEntity);
    }

    @Test
    void shouldFindByCourierId() {
        // given
        Long courierId = 1L;
        CourierLocationJpaEntity entity = mock(CourierLocationJpaEntity.class);
        CourierLocation domain = mock(CourierLocation.class);

        when(jpaRepository.findByCourierId(courierId)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        // when
        List<CourierLocation> result = repository.findByCourierId(courierId);

        // then
        assertThat(result).containsExactly(domain);
        verify(jpaRepository).findByCourierId(courierId);
        verify(mapper).toDomain(entity);
    }

    @Test
    void shouldFindAll() {
        // given
        CourierLocationJpaEntity entity = mock(CourierLocationJpaEntity.class);
        CourierLocation domain = mock(CourierLocation.class);

        when(jpaRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);

        // when
        List<CourierLocation> result = repository.findAll();

        // then
        assertThat(result).containsExactly(domain);
        verify(jpaRepository).findAll();
        verify(mapper).toDomain(entity);
    }
}
