package com.example.sanitas;

import com.example.sanitas.controller.MedicationController;
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
        Medication med1 = new Medication();
        med1.setId(1L);
        med1.setMedication("Ibuprofen");
        med1.setDose("400mg");

        Medication med2 = new Medication();
        med2.setId(2L);
        med2.setMedication("Paracetamol");
        med2.setDose("600mg");

        when(medicationService.getActiveMedications()).thenReturn(Arrays.asList(med1, med2));

        mockMvc.perform(get("/api/medications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].medication").value("Ibuprofen"))
                .andExpect(jsonPath("$[1].medication").value("Paracetamol"));
    }






}
