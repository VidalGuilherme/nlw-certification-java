package com.vidal.nlw_certification.modules.questions.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vidal.nlw_certification.modules.certifications.entities.CertificationEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "questions")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 50)
    private String technology;

    private String description;

    @OneToMany(
        targetEntity = QuestionAlternativeEntity.class,        
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JoinColumn(name = "question_id",  nullable=true, insertable=true, updatable=true)
    @Builder.Default
    private List<QuestionAlternativeEntity> alternatives = new ArrayList<>();

    @ManyToMany(mappedBy = "certificationQuestions", fetch = FetchType.LAZY)
    @JsonIgnoreProperties
    List<CertificationEntity> certifications;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default current_timestamp", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public void addAlternative(QuestionAlternativeEntity alternative) {
        alternatives.add(alternative);
        alternative.setQuestion(this);
    }
 
    public void removeAlternative(QuestionAlternativeEntity alternative) {
        alternatives.remove(alternative);
        alternative.setQuestion(null);
    }
}