package com.craftsoft.callDetailRecord.repositories;

import com.craftsoft.callDetailRecord.entity.CallRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CallRecordRepository extends JpaRepository<CallRecord, UUID> {
}
