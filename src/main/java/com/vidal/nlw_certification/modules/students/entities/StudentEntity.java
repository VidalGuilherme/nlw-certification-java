package com.vidal.nlw_certification.modules.students.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "students")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToMany(targetEntity = StudentCertificationEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    @JsonBackReference
    private List<StudentCertificationEntity> studentCertificationEntity;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public StudentEntity(UUID id) {
        this.id = id;
    }
}
