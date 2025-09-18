package com.example.sanitas.repository;

import com.example.sanitas.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByNextDate(LocalDate date);
}
