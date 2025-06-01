package com.kilicarslansoyler.tracking_service.infrastructure.persistence.repository;

import com.kilicarslansoyler.tracking_service.infrastructure.persistence.entity.EntryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryLogRepository extends JpaRepository<EntryLog, Long> {
}
