package com.example.sanitas;

// Import necessary JUnit5 & Mockito classes:
// TODO: Add "import" x3 for "Medication Entity", "Medication Repository", and "Medication Service".


import com.example.sanitas.dtos.MedicationResponse;
import com.example.sanitas.models.Medication;
import com.example.sanitas.models.Status;
import com.example.sanitas.repository.MedicationRepository;
import com.example.sanitas.service.MedicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// This annotation enables Mockito for JUnit5
@ExtendWith(MockitoExtension.class)
class MedicationServiceTest {

    // @Mock creates a mock (fake) version of the Repository
    @Mock
    private MedicationRepository medicationRepository;

    // @InjectMocks creates the Service and injects the mock Repository into it
    @InjectMocks
    private MedicationService medicationService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

//    @Test
//    void testCreateMedication_Success() {
//        // 1. ARRANGE - Setup the test data and define mock behavior
//        Medication medicationToSave = new Medication();
//        medicationToSave.setName("Ibuprofen");
//        medicationToSave.setDosage("200mg");
//        medicationToSave.setSchedule("09:00,21:00");
//
//        Medication savedMedication = new Medication();
//        savedMedication.setId(1L); // Simulate the ID assigned by the database
//        savedMedication.setName("Ibuprofen");
//        savedMedication.setDosage("200mg");
//        savedMedication.setSchedule("09:00,21:00");
//        savedMedication.setCreatedDate(LocalDateTime.now());
//        savedMedication.setIsActive(true);
//
//        // Tell the mock repository what to do when 'save' is called
//        when(medicationRepository.save(any(Medication.class))).thenReturn(savedMedication);
//
//        // 2. ACT - Call the method we are actually testing
//        Medication result = medicationService.createMedication(medicationToSave);
//
//        // 3. ASSERT - Verify the result is what we expected
//        assertNotNull(result); // Check that we got a result back
//        assertEquals(1L, result.getId()); // Check that the ID was set
//        assertEquals("Ibuprofen", result.getName()); // Check the name is correct
//        assertTrue(result.getIsActive()); // Check it's active by default
//
//        // VERIFY - Ensure the mock repository's save method was called exactly once
//        verify(medicationRepository, times(1)).save(medicationToSave);
//    }

    @Test
    void testGetActiveMedications(){
        Medication med1 = new Medication();
        med1.setId(1L);
        med1.setMedication("Ibuprofen");
        med1.setDose("400mg");
        med1.setNextDate(LocalDate.now());
        med1.setNextTime(LocalTime.of(9,0));
        med1.setFrequency("Una vez");
        med1.setStatus(Status.PENDING);

        Medication med2 = new Medication();
        med2.setId(2L);
        med2.setMedication("Paracetamol");
        med2.setDose("600mg");
        med2.setNextDate(LocalDate.now().plusDays(1));
        med2.setNextTime(LocalTime.of(12,0));
        med2.setFrequency("Cada 2 días");
        med2.setStatus(Status.PENDING);

        when (medicationRepository.findAll()).thenReturn(Arrays.asList(med1, med2));
        List<MedicationResponse> result = medicationService.getActiveMedications();

        assertEquals(2, result.size());
        assertEquals("Ibuprofen", result.get(0).medication());
        assertEquals("Paracetamol", result.get(1).medication());
    }
}
