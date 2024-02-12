package com.vidal.nlw_certification.modules.students.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidal.nlw_certification.modules.students.entities.StudentCertificationEntity;

public interface StudentCertificationRepository extends JpaRepository<StudentCertificationEntity, UUID>{

    Optional<StudentCertificationEntity> findByStudentEmailAndCertificationTechnology(String email, String technology);
    
}
