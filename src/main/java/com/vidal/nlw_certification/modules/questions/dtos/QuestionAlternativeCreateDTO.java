package com.vidal.nlw_certification.modules.questions.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionAlternativeCreateDTO(
    @NotBlank
    String description,
    @NotNull
    Boolean isCorrect
){}
