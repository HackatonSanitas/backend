package com.example.sanitas;

import com.example.sanitas.controller.MedicationController;
import com.example.sanitas.dtos.MedicationResponse;
import com.example.sanitas.models.Medication;
import com.example.sanitas.service.MedicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(MedicationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MedicationControllerTest {

    @MockBean
    private MedicationService medicationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllActiveMedications() throws Exception{
        MedicationResponse medResp1 = new MedicationResponse("Ibuprofen", "400mg", "Una vez", "2025-09-18 08:00", "PENDING");


        MedicationResponse medResp2 = new MedicationResponse("Paracetamol", "600mg", "Cada 2 días", "2025-09-19 12:00", "PENDING");

        when(medicationService.getActiveMedications()).thenReturn(Arrays.asList(medResp1, medResp2));

        mockMvc.perform(get("/api/medications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].medication").value("Ibuprofen"))
                .andExpect(jsonPath("$[1].medication").value("Paracetamol"));
    }






}
