package com.example.sanitas.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public record MedicationRequest(
   @NotBlank(message="El nombre de la medicación es obligatorio")
   String medication,

   @NotBlank(message="La dosis de la medicación es obligatoria")
   String dose,

   @NotNull(message="La fecha de inicio es obligatoria")
   LocalDate startDate,

   @NotNull(message="El número de días es obligatorio")
   @Positive(message="El número de días debe ser positivo")
   Integer days,

   @NotNull(message="El horario es obligatorio")
   LocalTime schedule
) {
}
