package com.vidal.nlw_certification.modules.students.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCertificationAnswerDTO {

    private String email;
    private String technology;
    private List<StudentQuestionAnswerDTO> questionsAnswers;
}