package com.vidal.nlw_certification.modules.questions.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAlternativeCreateDTO {

    @NotBlank
    private String description;
    @NotNull
    private Boolean isCorrect;
}
