package com.vidal.nlw_certification.modules.questions.dtos;

import com.vidal.nlw_certification.modules.questions.entities.QuestionAlternativeEntity;

import java.util.UUID;

public record QuestionAlternativeShowDTO(
        UUID id,
        String description
) {
    public QuestionAlternativeShowDTO(QuestionAlternativeEntity questionAlternative){
        this(questionAlternative.getId(), questionAlternative.getDescription());
    }
}
