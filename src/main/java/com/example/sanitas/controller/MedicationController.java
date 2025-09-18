package com.example.sanitas.controller;

import com.example.sanitas.dtos.MedicationRequest;
import com.example.sanitas.dtos.MedicationResponse;
import com.example.sanitas.models.Medication;
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

    @GetMapping
    public List<MedicationResponse> getAllActiveMedications() {
        return medicationService.getActiveMedications();
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
}
