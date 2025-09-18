package com.example.sanitas.medication_intakes;

import com.example.sanitas.medications.Medication;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "medications_intakes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationIntake {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @Column(name="take_at", nullable = false)
    private LocalDateTime takenAt;

    @Column
    private String notes;

    @PrePersist
    protected void onCreate(){
        if(takenAt == null){
            takenAt = LocalDateTime.now();
        }
    }
}
