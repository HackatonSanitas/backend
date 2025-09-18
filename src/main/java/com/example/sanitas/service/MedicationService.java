package com.example.sanitas.service;


import com.example.sanitas.models.Medication;
import com.example.sanitas.repository.MedicationIntakeRepository;
import com.example.sanitas.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationIntakeRepository medicationIntakeRepository;

    public List<Medication> getActiveMedications() {
        return medicationRepository.findAll();
    }
}