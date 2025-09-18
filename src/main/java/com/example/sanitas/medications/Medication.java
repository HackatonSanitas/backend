package com.example.sanitas.medications;

import com.example.sanitas.medication_intakes.MedicationIntake;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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

    @Column(nullable = false)
    private String frequency;

    @Column(nullable = false)
    private LocalDate nextDate;

    @Column
    private LocalTime nextTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @OneToMany (mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicationIntake> intakes = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        if(status == null) status = Status.PENDING;
    }

}
