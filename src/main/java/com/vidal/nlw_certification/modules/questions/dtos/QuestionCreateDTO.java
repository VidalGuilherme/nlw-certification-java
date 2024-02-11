package com.vidal.nlw_certification.modules.questions.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionCreateDTO {

    private String technology;
    private String description;
    private List<QuestionAlternativeCreateDTO> alternatives;
}
