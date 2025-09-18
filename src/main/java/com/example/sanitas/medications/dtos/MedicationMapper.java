package com.example.sanitas.medications.dtos;

import com.example.sanitas.medications.Medication;

public class MedicationMapper {

    public static Medication toEntity(MedicationRequest dto){
        return Medication.builder()
                .medication(dto.medication())
                .dose(dto.dose())
                .frequency(dto.frequency())
                .build();
    }

    public static MedicationResponse toDto(Medication medication){
        return new MedicationResponse(
                medication.getMedication(),
                medication.getDose(),
                medication.getFrequency(),
                medication.getIntakes().toString(),
                medication.getStatus().name()
        );
    }
}
