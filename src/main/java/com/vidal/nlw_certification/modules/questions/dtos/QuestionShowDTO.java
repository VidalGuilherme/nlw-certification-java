package com.vidal.nlw_certification.modules.questions.dtos;

import com.vidal.nlw_certification.modules.questions.entities.QuestionEntity;

import java.util.List;
import java.util.UUID;

public record QuestionShowDTO (
    UUID id,
    String technology,
    String description,
    List<QuestionAlternativeShowDTO> alternatives
) {
    public QuestionShowDTO(QuestionEntity question){
        this(question.getId(), question.getTechnology(), question.getDescription(), question.getAlternatives().stream().map(QuestionAlternativeShowDTO::new).toList());
    }
}
