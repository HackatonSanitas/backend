package com.example.sanitas.medications;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


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
    private LocalDate scheduleDate;

    @Column(nullable=false)
    private LocalTime scheduleTime;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
    }

}
