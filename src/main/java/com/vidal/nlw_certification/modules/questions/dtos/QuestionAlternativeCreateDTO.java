package com.vidal.nlw_certification.modules.questions.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionAlternativeCreateDTO {

    private String description;
    private Boolean isCorrect;
}
