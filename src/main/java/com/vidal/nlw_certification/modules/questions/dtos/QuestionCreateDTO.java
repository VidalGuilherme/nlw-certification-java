package com.vidal.nlw_certification.modules.questions.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionCreateDTO {

    @NotBlank
    private String technology;
    @NotBlank
    private String description;
    @NotEmpty
    private List<QuestionAlternativeCreateDTO> alternatives;
}
