package com.example.sanitas.controller;

import com.example.sanitas.dtos.MedicationResponse;
import com.example.sanitas.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;

    @GetMapping
    public List<MedicationResponse> getAllActiveMedications(){
        return medicationService.getActiveMedications();
    }
}