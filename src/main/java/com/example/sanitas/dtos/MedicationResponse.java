package com.example.sanitas.dtos;

public record MedicationResponse(
        String medication,
        String dose,
        String intake,
        String status
) {
}
