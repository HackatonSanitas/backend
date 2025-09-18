package com.example.sanitas.config;

import com.example.sanitas.dtos.MedicationRequest;
import com.example.sanitas.dtos.MedicationMapper;
import com.example.sanitas.models.Medication;
import com.example.sanitas.models.MedicationIntake;
import com.example.sanitas.models.Status;
import com.example.sanitas.repository.MedicationRepository;
import com.example.sanitas.repository.MedicationIntakeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(MedicationRepository medicationRepository,
                                   MedicationIntakeRepository intakeRepository) {
        return args -> {

            // Crear medicaciones de ejemplo
            MedicationRequest medReq1 = new MedicationRequest(
                    "Ibuprofen",
                    "400mg",
                    LocalDate.now(),
                    1,
                    LocalTime.of(8, 0)
            );

            MedicationRequest medReq2 = new MedicationRequest(
                    "Paracetamol",
                    "500mg",
                    LocalDate.now().plusDays(1),
                    2,
                    LocalTime.of(12, 0)
            );

            // Convertir DTOs a entidades
            Medication med1 = MedicationMapper.toEntity(medReq1);
            Medication med2 = MedicationMapper.toEntity(medReq2);

            // Guardar las medicaciones
            medicationRepository.saveAll(List.of(med1, med2));

            // Crear intakes de ejemplo
            MedicationIntake intake1 = MedicationIntake.builder()
                    .medication(med1)
                    .takenAt(LocalDateTime.now().minusHours(1))
                    .notes("Tomada con desayuno")
                    .build();

            MedicationIntake intake2 = MedicationIntake.builder()
                    .medication(med2)
                    .takenAt(LocalDateTime.now())
                    .notes("Tomada antes de comer")
                    .build();

            // Guardar intakes
            intakeRepository.saveAll(List.of(intake1, intake2));

            System.out.println("Database seeded successfully!");
        };
    }
}

