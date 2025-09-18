package com.example.sanitas.service;

// TODO: Add "import" x2 for "Medication Entity" and "Medication Service"

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor // Lombok - It should creates constructor with required (final) fields
public class MedicationService {
    private final MedicationRepository medicationRepository;

    public Medication createMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    public List<Medication> getActiveMedications() {
        return medicationRepository.findByIsActiveTrue();
    }

    public void deleteMedication(Long id) {
        // Soft delete
        Medication medication = medicationRepository.findById(id).orElseThrow();
        medication.setIsActive(false);
        medicationRepository.save(medication);
    }

    public Medication markAsTaken(Long id) {
        Medication medication = medicationRepository.findById(id).orElseThrow();
        medication.setLastTaken(LocalDateTime.now());
        return medicationRepository.save(medication);
    }
}