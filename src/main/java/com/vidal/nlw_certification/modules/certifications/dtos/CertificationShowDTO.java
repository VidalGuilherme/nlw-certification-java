package com.vidal.nlw_certification.modules.certifications.dtos;

import java.util.List;
import java.util.UUID;

import com.vidal.nlw_certification.modules.questions.dtos.QuestionShowDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationShowDTO {

    private UUID id;
    private String technology;
    private List<QuestionShowDTO> questions;
}
