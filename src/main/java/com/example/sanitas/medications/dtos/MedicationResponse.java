package com.example.sanitas.medications.dtos;

public record MedicationResponse(
        String medication,
        String dose,
        String frequency,
        String intake,
        String status
) {
}
