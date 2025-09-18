package com.example.sanitas.controller;

// TODO: Add "import" x2 for "Medication Entity" and "Medication Service"

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// TODO: Add Swagger dependency

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
@Tag(name = "Medication API", description = "Endpoints for managing medication records")
public class MedicationController {
    private final MedicationService medicationService;

    @GetMapping
    @Operation(summary = "Get all active medications",
               description = "Retrieves a list of all medications that are currently active.")
    public List<Medication> getAllActiveMedications() {
        return medicationService.getActiveMedications();
    }

    @PostMapping
    @Operation(summary = "Create a new medication record",
               description = "Adds a new medication to the system with a name, dosage, and schedule.")
    public Medication createMedication(@RequestBody Medication medication) {
        return medicationService.createMedication(medication);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft-delete a medication by ID",
               description = "Sets the 'isActive' status of a medication to false, effectively soft-deleting it from the active list.")
    public void deleteMedication(@PathVariable Long id) {
        medicationService.deleteMedication(id);
    }

    @PostMapping("/{id}/taken")
    @Operation(summary = "Mark a medication as taken",
               description = "Updates the 'lastTaken' timestamp for a medication, indicating it has been taken.")
    public Medication markAsTaken(@PathVariable Long id) {
        return medicationService.markAsTaken(id);
    }
}