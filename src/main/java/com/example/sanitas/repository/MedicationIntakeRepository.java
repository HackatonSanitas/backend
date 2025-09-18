package com.example.sanitas.repository;

import com.example.sanitas.models.MedicationIntake;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationIntakeRepository extends JpaRepository<MedicationIntake, Long> {
}
