package com.example.sanitas;

import com.example.sanitas.dtos.MedicationRequest;
import com.example.sanitas.dtos.MedicationResponse;
import com.example.sanitas.models.Medication;
import com.example.sanitas.models.MedicationIntake;
import com.example.sanitas.models.Status;
import com.example.sanitas.repository.MedicationIntakeRepository;
import com.example.sanitas.repository.MedicationRepository;
import com.example.sanitas.service.MedicationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private MedicationIntakeRepository medicationIntakeRepository;

    @InjectMocks
    private MedicationService medicationService;

    private Medication med1;
    private Medication med2;

    @BeforeEach
    void setUp() {
        med1 = Medication.builder()
                .id(1L)
                .medication("Ibuprofeno")
                .dose("400mg")
                .nextDate(LocalDate.now())
                .nextTime(LocalTime.of(9, 0))
                .frequency("Una vez")
                .status(Status.PENDIENTE)
                .build();

        med2 = Medication.builder()
                .id(2L)
                .medication("Paracetamol")
                .dose("600mg")
                .nextDate(LocalDate.now().plusDays(1))
                .nextTime(LocalTime.of(12, 0))
                .frequency("Cada 2 días")
                .status(Status.PENDIENTE)
                .build();
    }
    @Nested
    @DisplayName("GET medications")
    class GetMedicationTests{
        @Test
        void testGetActiveMedications(){
            when (medicationRepository.findAll()).thenReturn(Arrays.asList(med1, med2));
            List<MedicationResponse> result = medicationService.getActiveMedications();

            assertEquals(2, result.size());
            assertEquals("Ibuprofeno", result.get(0).medication());
            assertEquals("Paracetamol", result.get(1).medication());
        }
        @Test
        void testGetTodayMedications() {
            when(medicationRepository.findByNextDate(LocalDate.now())).thenReturn(List.of(med1));
            List<MedicationResponse> result = medicationService.getTodayMedications();

            assertEquals(1, result.size());
            assertEquals("Ibuprofeno", result.get(0).medication());
        }
        @Test
        void testGetTomorrowMedications() {
            when(medicationRepository.findByNextDate(LocalDate.now().plusDays(1))).thenReturn(List.of(med2));
            List<MedicationResponse> result = medicationService.getTomorrowMedications();

            assertEquals(1, result.size());
            assertEquals("Paracetamol", result.get(0).medication());
        }
        @Test
        void testGetWeekMedications() {
            when(medicationRepository.findAll()).thenReturn(Arrays.asList(med1, med2));
            List<MedicationResponse> result = medicationService.getWeekMedications();

            assertEquals(2, result.size());
        }
        @Test
        void testGetMedicationHistory() {
            MedicationIntake intake1 = MedicationIntake.builder()
                    .id(1L)
                    .takenAt(LocalDateTime.now().minusDays(1))
                    .build();
            MedicationIntake intake2 = MedicationIntake.builder()
                    .id(2L)
                    .takenAt(LocalDateTime.now())
                    .build();

            when(medicationIntakeRepository.findAll()).thenReturn(Arrays.asList(intake1, intake2));
            List<MedicationIntake> result = medicationService.getMedicationHistory();
            assertEquals(2, result.size());
            assertEquals(intake2, result.get(0));
            assertEquals(intake1, result.get(1));
        }
    }

    @Nested
    @DisplayName("CREATE medication")
    class CreateMedicationTest {

        @Test
        void testCreateMedication() {
            MedicationRequest request = new MedicationRequest("Ibuprofeno", "400mg", LocalDate.now(), 1, LocalTime.of(9, 0));
            when(medicationRepository.save(any(Medication.class))).thenReturn(med1);

            MedicationResponse response = medicationService.createMedication(request);

            assertEquals("Ibuprofeno", response.medication());
            assertEquals("400mg", response.dose());
            assertEquals(Status.PENDIENTE.name(), response.status());
        }

        @Test
        void testMarkAsTaken() {
            when(medicationRepository.findById(1L)).thenReturn(Optional.of(med1));
            when(medicationRepository.save(any(Medication.class))).thenReturn(med1);
            when(medicationIntakeRepository.save(any(MedicationIntake.class))).thenReturn(new MedicationIntake());

            Medication result = medicationService.markAsTaken(1L);

            assertEquals(Status.TOMADO, result.getStatus());
            verify(medicationIntakeRepository, times(1)).save(any(MedicationIntake.class));
        }

        @Test
        void testMarkAsTakenNotFound() {
            when(medicationRepository.findById(5L)).thenReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class, () -> medicationService.markAsTaken(5L));
        }
    }
    @Nested
    @DisplayName("UPDATE medication")
    class UpdateMedicationTest {
        @Test
        void testUpdateMedication() {
            MedicationRequest request = new MedicationRequest("Ibuprofeno actualizado", "500mg", LocalDate.now(), 2, LocalTime.of(10, 0));
            when(medicationRepository.findById(1L)).thenReturn(Optional.of(med1));
            when(medicationRepository.save(any(Medication.class))).thenReturn(med1);

            MedicationResponse response = medicationService.updateMedication(1L, request);

            assertEquals("Ibuprofeno actualizado", response.medication());
        }

        @Test
        void testUpdateMedicationNotFound() {
            when(medicationRepository.findById(3L)).thenReturn(Optional.empty());
            MedicationRequest request = new MedicationRequest("Nada", "100mg", LocalDate.now(), 1, LocalTime.of(9, 0));

            assertThrows(EntityNotFoundException.class, () -> medicationService.updateMedication(3L, request));
        }
    }
    @Nested
    @DisplayName("DELETE medication")
    class DeleteMedicationTest {

        @Test
        void testDeleteMedication() {
            when(medicationRepository.findById(1L)).thenReturn(Optional.of(med1));

            assertDoesNotThrow(() -> medicationService.deleteMedication(1L));
            verify(medicationRepository, times(1)).delete(med1);
        }

        @Test
        void testDeleteMedicationNotFound() {
            when(medicationRepository.findById(3L)).thenReturn(Optional.empty());
            assertThrows(EntityNotFoundException.class, () -> medicationService.deleteMedication(3L));
        }
    }
}
