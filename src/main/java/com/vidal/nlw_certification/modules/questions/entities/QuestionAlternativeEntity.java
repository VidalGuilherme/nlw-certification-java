package com.vidal.nlw_certification.modules.questions.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "question_alternatives")
public class QuestionAlternativeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    private boolean isCorrect;

    @ManyToOne(targetEntity = QuestionEntity.class)
    @JoinColumn(name="question_id", nullable=false)
    private QuestionEntity question;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}