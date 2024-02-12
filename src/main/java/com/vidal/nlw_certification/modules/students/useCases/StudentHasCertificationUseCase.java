package com.vidal.nlw_certification.modules.students.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidal.nlw_certification.modules.students.dtos.StudentHasCertificationDTO;
import com.vidal.nlw_certification.modules.students.entities.StudentCertificationEntity;
import com.vidal.nlw_certification.modules.students.repositories.StudentCertificationRepository;

@Service
public class StudentHasCertificationUseCase {

    @Autowired
    private StudentCertificationRepository studentCertificationRepository;

    public boolean execute(StudentHasCertificationDTO dto) {
        Optional<StudentCertificationEntity> result = this.studentCertificationRepository.findByStudentEmailAndCertificationTechnology(dto.getEmail(),
                dto.getTechnology());
        if (!result.isEmpty()) {
            return true;
        }
        return false;
    }
}