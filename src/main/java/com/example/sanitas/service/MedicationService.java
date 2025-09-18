package com.example.sanitas.service;


import com.example.sanitas.dtos.MedicationMapper;
import com.example.sanitas.dtos.MedicationRequest;
import com.example.sanitas.dtos.MedicationResponse;
import com.example.sanitas.models.Medication;
import com.example.sanitas.models.MedicationIntake;
import com.example.sanitas.models.Status;
import com.example.sanitas.repository.MedicationIntakeRepository;
import com.example.sanitas.repository.MedicationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import static com.example.sanitas.models.Status.PENDING;

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

    public MedicationResponse createMedication(MedicationRequest request) {
        Medication medication = MedicationMapper.toEntity(request);
        medication.setStatus(PENDING);
        Medication savedMedication = medicationRepository.save(medication);
        return MedicationMapper.toDto(savedMedication);
    }

    @Transactional
    public Medication markAsTaken(Long id) {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Medicamento no encontrado"));

        medication.setStatus(Status.TAKEN);
        medicationRepository.save(medication);

        MedicationIntake intake = new MedicationIntake();
        intake.setMedication(medication);
        intake.setTakenAt(LocalDateTime.now());
        medicationIntakeRepository.save(intake);

        return medication;
    }

    public List<MedicationResponse> getTodayMedications() {
        LocalDate today = LocalDate.now();
        return medicationRepository.findByNextDate(today)
                .stream()
                .map(MedicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<MedicationResponse> getTomorrowMedications() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        return medicationRepository.findByNextDate(tomorrow)
                .stream()
                .map(MedicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<MedicationResponse> getWeekMedications() {
        LocalDate today = LocalDate.now();
        LocalDate weekEnd = today.plusDays(6);
        return medicationRepository.findAll()
                .stream()
                .filter(m -> !m.getNextDate().isBefore(today) && !m.getNextDate().isAfter(weekEnd))
                .map(MedicationMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<MedicationIntake> getMedicationHistory() {
        return medicationIntakeRepository.findAll()
                .stream()
                .sorted((a, b) -> b.getTakenAt().compareTo(a.getTakenAt()))
                .collect(Collectors.toList());
    }
}