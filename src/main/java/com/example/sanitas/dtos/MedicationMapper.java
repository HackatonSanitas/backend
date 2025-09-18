package com.example.sanitas.dtos;

import com.example.sanitas.models.Medication;
import com.example.sanitas.models.Status;

public class MedicationMapper {

    public static Medication toEntity(MedicationRequest dto){
        String frequency = dto.days() == 1 ? "Una vez" : "Cada " + dto.days() + " días";
        return Medication.builder()
                .medication(dto.medication())
                .dose(dto.dose())
                .frequency(frequency)
                .nextDate(dto.startDate())
                .nextTime(dto.schedule())
                .status(Status.PENDING)
                .build();
    }

    public static MedicationResponse toDto(Medication medication){
        return new MedicationResponse(
                medication.getMedication(),
                medication.getDose(),
                medication.getNextDate().toString() + " " + (medication.getNextTime() != null ? medication.getNextTime().toString() : ""),
                medication.getStatus().name()
        );
    }
}
