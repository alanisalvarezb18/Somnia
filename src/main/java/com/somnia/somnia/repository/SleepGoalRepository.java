package com.somnia.somnia.repository;

import com.somnia.somnia.model.SleepGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SleepGoalRepository extends JpaRepository<SleepGoal, Integer> {
    Optional<SleepGoal> findByUsuarioId(Integer usuarioId);
}