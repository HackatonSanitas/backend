package com.example.sanitas.service;


import com.example.sanitas.dtos.MedicationMapper;
import com.example.sanitas.dtos.MedicationResponse;
import com.example.sanitas.models.Medication;
import com.example.sanitas.repository.MedicationIntakeRepository;
import com.example.sanitas.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationService {
    private final MedicationRepository medicationRepository;
    private final MedicationIntakeRepository medicationIntakeRepository;

    public List<MedicationResponse> getActiveMedications() {
        return medicationRepository.findAll()
                .stream()
                .map(MedicationMapper::toDto)
                .collect(Collectors.toList());
    }
}