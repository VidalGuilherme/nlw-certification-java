package com.vidal.nlw_certification.modules.certifications.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidal.nlw_certification.modules.certifications.dtos.CertificationCreateDTO;
import com.vidal.nlw_certification.modules.certifications.entities.CertificationEntity;
import com.vidal.nlw_certification.modules.certifications.repositories.CertificationRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/certifications")
public class CertificationController {
    
    @Autowired
    CertificationRepository certificationRepository;

    // CRUD
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCertification(@PathVariable(value = "id") UUID id){
        Optional<CertificationEntity> question = certificationRepository.findById(id);
        if(question.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certification Not Found");
        }        
        return ResponseEntity.ok().body(question.get());
    }

    @PostMapping
    public ResponseEntity<CertificationEntity> createCertification(@RequestBody @Valid CertificationCreateDTO questionDto){
        CertificationEntity CertificationEntity = new CertificationEntity();
        BeanUtils.copyProperties(questionDto, CertificationEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(certificationRepository.save(CertificationEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCertification(@PathVariable(value = "id") UUID id){
        Optional<CertificationEntity> question = certificationRepository.findById(id);
        if(question.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certification Not Found");
        }
        certificationRepository.delete(question.get());
        return ResponseEntity.ok().body("Certification deleted with success!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCertification(@PathVariable(value = "id") UUID id, @RequestBody @Valid CertificationCreateDTO questionDto){
        Optional<CertificationEntity> question = certificationRepository.findById(id);
        if(question.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certification Not Found");
        }
        CertificationEntity CertificationEntity = question.get();
        BeanUtils.copyProperties(questionDto, CertificationEntity);
        return ResponseEntity.status(HttpStatus.OK).body(certificationRepository.save(CertificationEntity));
    }  
}
