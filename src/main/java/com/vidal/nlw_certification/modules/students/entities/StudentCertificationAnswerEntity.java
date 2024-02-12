package com.vidal.nlw_certification.modules.students.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.vidal.nlw_certification.modules.questions.entities.QuestionAlternativeEntity;
import com.vidal.nlw_certification.modules.questions.entities.QuestionEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student_certification_answers")
@Builder
public class StudentCertificationAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(targetEntity = StudentCertificationEntity.class)
    @JoinColumn(name = "student_certification_id")
    private StudentCertificationEntity studentCertificationEntity;

    @OneToOne(targetEntity = QuestionEntity.class)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @OneToOne(targetEntity = QuestionAlternativeEntity.class)
    @JoinColumn(name = "answer_id", nullable = false)
    private QuestionAlternativeEntity answer;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}