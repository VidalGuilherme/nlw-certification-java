package com.vidal.nlw_certification.modules.students.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidal.nlw_certification.modules.certifications.controllers.CertificationController;
import com.vidal.nlw_certification.modules.students.dtos.StudentCertificationAnswerDTO;
import com.vidal.nlw_certification.modules.students.dtos.StudentCertificationDTO;
import com.vidal.nlw_certification.modules.students.dtos.StudentHasCertificationDTO;
import com.vidal.nlw_certification.modules.students.entities.StudentCertificationEntity;
import com.vidal.nlw_certification.modules.students.useCases.StudentCertificationAnswersUseCase;
import com.vidal.nlw_certification.modules.students.useCases.StudentHasCertificationUseCase;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/students")
public class StudentController {

    // Preciso usar o meu USECASE
    @Autowired
    private StudentHasCertificationUseCase studentHasCertificationUseCase;

    @Autowired
    private StudentCertificationAnswersUseCase studentCertificationAnswersUseCase;

    @PostMapping("/has_certification")
    public String hasCertification(@RequestBody @Valid StudentHasCertificationDTO studentHasCertificationDTO) {
        var result = this.studentHasCertificationUseCase.execute(studentHasCertificationDTO);
        if (result) {
            return "Usuário já fez a prova";
        }

        return "Usuário pode fazer a prova";
    }

    @PostMapping("/certification/answer")
    public ResponseEntity<Object> certificationAnswer(
            @RequestBody @Valid StudentCertificationAnswerDTO studentCertificationAnswerDTO) {
        try {
            StudentCertificationEntity result = studentCertificationAnswersUseCase.execute(studentCertificationAnswerDTO);            
            return ResponseEntity.ok().body(convertEntityToDto(result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private StudentCertificationDTO convertEntityToDto(StudentCertificationEntity entity){
        StudentCertificationDTO dto = StudentCertificationDTO.builder()            
            .id(entity.getId())
            .email(entity.getStudent().getEmail())
            .grade(entity.getGrade())
            .certification(CertificationController.convertCertificationToDto(entity.getCertification()))
            .build();

        List<StudentCertificationAnswerDTO> answersDto = entity.getStudentCertificationAnswers()
            .stream()
            .map(answers -> {
                return StudentCertificationAnswerDTO.builder()
                    .build();                      
            })
            .collect(Collectors.toList());
        
        dto.setAnswers(answersDto);

        return dto;
    }
}
