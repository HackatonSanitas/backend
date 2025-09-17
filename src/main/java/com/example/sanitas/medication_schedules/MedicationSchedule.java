package com.example.sanitas.medication_schedules;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "medications_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationSchedule {
}
