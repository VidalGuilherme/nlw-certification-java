package com.vidal.nlw_certification.modules.students.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidal.nlw_certification.modules.students.entities.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID>{

    Optional<StudentEntity> findByEmail(String email);
    
}
