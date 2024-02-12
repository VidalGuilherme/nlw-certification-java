package com.vidal.nlw_certification.modules.students.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentHasCertificationDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String technology;
}