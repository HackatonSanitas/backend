package com.example.sanitas.controller;

import com.example.sanitas.dtos.MedicationRequest;
import com.example.sanitas.dtos.MedicationResponse;
import com.example.sanitas.models.Medication;
import com.example.sanitas.models.MedicationIntake;
import com.example.sanitas.service.MedicationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;

    @PostMapping
    public ResponseEntity<?> createMedication(@Valid @RequestBody MedicationRequest request) {
        try {
            MedicationResponse response = medicationService.createMedication(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al crear el medicamento: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/take")
    public ResponseEntity<?> markMedicationAsTaken(@PathVariable Long id) {
        try {
            Medication updatedMedication = medicationService.markAsTaken(id);
            return ResponseEntity.ok(updatedMedication);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Medicamento no encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al marcar como tomado: " + e.getMessage());
        }
    }

    @GetMapping
    public List<MedicationResponse> getAllActiveMedications() {
        return medicationService.getActiveMedications();
    }

    @GetMapping("/today")
    public List<MedicationResponse> getTodayMedications() {
        return medicationService.getTodayMedications();
    }

    @GetMapping("/tomorrow")
    public List<MedicationResponse> getTomorrowMedications() {
        return medicationService.getTomorrowMedications();
    }

    @GetMapping("/week")
    public List<MedicationResponse> getWeekMedications() {
        return medicationService.getWeekMedications();
    }

    @GetMapping("/history")
    public List<MedicationIntake> getMedicationHistory() {
        return medicationService.getMedicationHistory();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMedication(@PathVariable Long id,
                                              @Valid @RequestBody MedicationRequest request) {
        try {
            MedicationResponse response = medicationService.updateMedication(id, request);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Error al actualizar el medicamento: " + e.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
        return ResponseEntity.ok("Medicamento eliminado correctamente");
    }
}
