package com.vidal.nlw_certification.modules.questions.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record QuestionCreateDTO (
    @NotBlank
    String technology,
    @NotBlank
    String description,
    @NotEmpty
    List<QuestionAlternativeCreateDTO> alternatives
){ }
