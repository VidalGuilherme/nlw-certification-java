package com.vidal.nlw_certification.modules.students.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentQuestionAnswerDTO {

    private UUID questionId;
    private UUID alternativeId;
    private boolean isCorrect;
}