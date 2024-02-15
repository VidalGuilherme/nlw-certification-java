package com.vidal.nlw_certification.modules.questions.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vidal.nlw_certification.modules.questions.dtos.QuestionAlternativeCreateDTO;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "question_alternatives")
public class QuestionAlternativeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String description;

    private boolean isCorrect;

    @ManyToOne(targetEntity = QuestionEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public QuestionAlternativeEntity(){}

    public QuestionAlternativeEntity(QuestionAlternativeCreateDTO alternativeDto){
        this.setDescription(alternativeDto.description());
        this.setCorrect(alternativeDto.isCorrect());
    }
}