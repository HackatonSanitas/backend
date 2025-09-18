package com.example.sanitas;

import com.example.sanitas.controller.MedicationController;
import com.example.sanitas.dtos.MedicationRequest;
import com.example.sanitas.dtos.MedicationResponse;
import com.example.sanitas.service.MedicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;


import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateMedication_Success() throws Exception {
        MedicationRequest request = new MedicationRequest(
                "Aspirina",
                "100mg",
                LocalDate.of(2025, 9, 18),
                7,
                LocalTime.of(9, 0)
        );

        MedicationResponse response = new MedicationResponse("Aspirina", "100mg", "2025-09-18 09:00", "PENDING");

        when(medicationService.createMedication(any(MedicationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/medications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.medication").value("Aspirina"))
                .andExpect(jsonPath("$.dose").value("100mg"));
    }

    @Test
    public void testCreateMedication_Error() throws Exception {
        MedicationRequest request = new MedicationRequest(
                "Aspirina",
                "100mg",
                LocalDate.of(2025, 9, 18),
                7,
                LocalTime.of(9, 0)
        );

        when(medicationService.createMedication(any(MedicationRequest.class)))
                .thenThrow(new RuntimeException("Error de validación"));

        mockMvc.perform(post("/api/medications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error al crear el medicamento: Error de validación"));
    }

    @Test
    public void testCreateMedication_ValidationError() throws Exception {
        MedicationRequest invalidRequest = new MedicationRequest(
                "",
                "",
                null,
                -1,
                null
        );

        mockMvc.perform(post("/api/medications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllActiveMedications() throws Exception{
        MedicationResponse medResp1 = new MedicationResponse("Ibuprofen", "400mg", "2025-09-18 08:00", "PENDING");
        MedicationResponse medResp2 = new MedicationResponse("Paracetamol", "600mg", "2025-09-19 12:00", "PENDING");

        when(medicationService.getActiveMedications()).thenReturn(Arrays.asList(medResp1, medResp2));

        mockMvc.perform(get("/api/medications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].medication").value("Ibuprofen"))
                .andExpect(jsonPath("$[1].medication").value("Paracetamol"));
    }
}
