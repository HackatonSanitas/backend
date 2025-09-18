package com.example.sanitas.service;


import com.example.sanitas.dtos.MedicationMapper;
import com.example.sanitas.dtos.MedicationRequest;
import com.example.sanitas.dtos.MedicationResponse;
import com.example.sanitas.models.Medication;
import com.example.sanitas.repository.MedicationIntakeRepository;
import com.example.sanitas.repository.MedicationRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public MedicationResponse updateMedication(Long id, MedicationRequest request) {
        Medication existingMedication = medicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento no encontrado con ID: " + id));

        existingMedication.setMedication(request.medication());
        existingMedication.setDose(request.dose());
        existingMedication.setNextDate(request.startDate());
        existingMedication.setNextTime(request.schedule());

        String frequency = request.days() == 1 ? "Una vez" : "Cada " + request.days() + " días";
        existingMedication.setFrequency(frequency);

        Medication updatedMedication = medicationRepository.save(existingMedication);
        return MedicationMapper.toDto(updatedMedication);
    }

}