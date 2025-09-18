package com.example.sanitas;

// Import necessary JUnit5 & Mockito classes:
// TODO: Add "import" x3 for "Medication Entity", "Medication Repository", and "Medication Service".


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

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

    @Test
    void testCreateMedication_Success() {
        // 1. ARRANGE - Setup the test data and define mock behavior
        Medication medicationToSave = new Medication();
        medicationToSave.setName("Ibuprofen");
        medicationToSave.setDosage("200mg");
        medicationToSave.setSchedule("09:00,21:00");

        Medication savedMedication = new Medication();
        savedMedication.setId(1L); // Simulate the ID assigned by the database
        savedMedication.setName("Ibuprofen");
        savedMedication.setDosage("200mg");
        savedMedication.setSchedule("09:00,21:00");
        savedMedication.setCreatedDate(LocalDateTime.now());
        savedMedication.setIsActive(true);

        // Tell the mock repository what to do when 'save' is called
        when(medicationRepository.save(any(Medication.class))).thenReturn(savedMedication);

        // 2. ACT - Call the method we are actually testing
        Medication result = medicationService.createMedication(medicationToSave);

        // 3. ASSERT - Verify the result is what we expected
        assertNotNull(result); // Check that we got a result back
        assertEquals(1L, result.getId()); // Check that the ID was set
        assertEquals("Ibuprofen", result.getName()); // Check the name is correct
        assertTrue(result.getIsActive()); // Check it's active by default

        // VERIFY - Ensure the mock repository's save method was called exactly once
        verify(medicationRepository, times(1)).save(medicationToSave);
    }
}
