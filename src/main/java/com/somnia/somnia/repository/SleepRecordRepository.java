package com.somnia.somnia.repository;

import com.somnia.somnia.model.SleepRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SleepRecordRepository extends JpaRepository<SleepRecord, Integer> {
    List<SleepRecord> findAllByOrderByFechaRegistroDesc();
    List<SleepRecord> findByUsuarioId(Integer usuarioId);
    List<SleepRecord> findByUsuarioIdOrderByFechaRegistroDesc(Integer usuarioId);
}