package com.example.sanitas.medications.dtos;

import jakarta.validation.constraints.NotBlank;

public record MedicationRequest(
   @NotBlank(message="El nombre de la medicación es obligatorio")
   String medication,

   @NotBlank(message="La dosis de la medicación es obligatoria")
   String dose,

   @NotBlank(message="La frecuencia de la medicación es obligatoria")
   String frequency
) {
}
