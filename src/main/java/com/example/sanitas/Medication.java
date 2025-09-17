package com.example.sanitas;

import com.example.sanitas.medication_schedules.MedicationSchedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "medications")
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Medication {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String medication;

    @Column(nullable = false)
    private String dose;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicationSchedule> schedules;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }

}
