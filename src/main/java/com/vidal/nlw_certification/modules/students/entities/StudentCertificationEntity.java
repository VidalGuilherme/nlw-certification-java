package com.vidal.nlw_certification.modules.students.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.vidal.nlw_certification.modules.certifications.entities.CertificationEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student_certifications")
@Builder
public class StudentCertificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(targetEntity = CertificationEntity.class)
    @JoinColumn(name = "certification_id", nullable = false)
    private CertificationEntity certification;

    @Column(length = 10)
    private int grade;

    @ManyToOne(targetEntity = StudentEntity.class)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @OneToMany(targetEntity = StudentCertificationAnswerEntity.class, cascade = CascadeType.ALL)
    @JoinColumn(name =  "student_certification_id")
    List<StudentCertificationAnswerEntity> studentCertificationAnswers;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}