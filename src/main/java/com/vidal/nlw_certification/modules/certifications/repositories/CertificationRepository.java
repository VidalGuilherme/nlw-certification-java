package com.vidal.nlw_certification.modules.certifications.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidal.nlw_certification.modules.certifications.entities.CertificationEntity;

public interface CertificationRepository extends JpaRepository<CertificationEntity, UUID>{    

    List<CertificationEntity> findByTechnology(String technology);
}
