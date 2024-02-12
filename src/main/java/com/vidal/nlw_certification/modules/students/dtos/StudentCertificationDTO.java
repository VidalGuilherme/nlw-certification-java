package com.vidal.nlw_certification.modules.students.dtos;

import java.util.List;
import java.util.UUID;

import com.vidal.nlw_certification.modules.certifications.dtos.CertificationShowDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCertificationDTO {

    private UUID id;
    private String email;
    private Integer grade;
    private CertificationShowDTO certification;
    private List<StudentCertificationAnswerDTO> answers;
}