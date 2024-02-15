package com.vidal.nlw_certification.modules.certifications.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
import com.vidal.nlw_certification.modules.certifications.dtos.CertificationShowDTO;
import com.vidal.nlw_certification.modules.certifications.entities.CertificationEntity;
import com.vidal.nlw_certification.modules.certifications.repositories.CertificationRepository;
import com.vidal.nlw_certification.modules.questions.controllers.QuestionController;
import com.vidal.nlw_certification.modules.questions.dtos.QuestionShowDTO;
import com.vidal.nlw_certification.modules.questions.entities.QuestionEntity;
import com.vidal.nlw_certification.modules.questions.repositories.QuestionRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/certifications")
public class CertificationController {
    
    @Autowired
    CertificationRepository certificationRepository;

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("")
    public ResponseEntity<List<CertificationShowDTO>> findAll() {
        List<CertificationEntity> result = certificationRepository.findAll();
        List<CertificationShowDTO> certifications = new ArrayList<>();
        if(!result.isEmpty()){
            certifications = result.stream()
                .map(certification -> convertCertificationToDto(certification))
                .collect(Collectors.toList());
        }
        return ResponseEntity.ok().body(certifications);
    }

    @GetMapping("/technology/{technology}")
    public ResponseEntity<List<CertificationShowDTO>> findByTechnology(@PathVariable(value = "technology") String technology) {
        List<CertificationEntity> result = certificationRepository.findByTechnology(technology);
        List<CertificationShowDTO> certifications = new ArrayList<>();
        if(!result.isEmpty()){
            certifications = result.stream()
                .map(certification -> convertCertificationToDto(certification))
                .collect(Collectors.toList());
        }
        return ResponseEntity.ok().body(certifications);
    }
    

    public static CertificationShowDTO convertCertificationToDto(CertificationEntity certification){

        CertificationShowDTO certificationDto = CertificationShowDTO.builder()
            .id(certification.getId())
            .technology(certification.getTechnology())
            .build();

        List<QuestionShowDTO> questionsDto = certification.getCertificationQuestions()
            .stream()
            .map(QuestionShowDTO::new)
            .collect(Collectors.toList());
        
        certificationDto.setQuestions(questionsDto);

        return certificationDto;
    }

    // CRUD
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCertification(@PathVariable(value = "id") UUID id){
        Optional<CertificationEntity> certification = certificationRepository.findById(id);
        if(certification.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certification Not Found");
        }        
        return ResponseEntity.ok().body(convertCertificationToDto(certification.get()));
    }

    @PostMapping
    public ResponseEntity<CertificationEntity> createCertification(@RequestBody @Valid CertificationCreateDTO certificationDto){
        CertificationEntity certificationEntity = new CertificationEntity();
        certificationEntity.setTechnology(certificationDto.getTechnology());
        List<QuestionEntity> questions = questionRepository.findAllById(certificationDto.getQuestionsId());
        certificationEntity.setCertificationQuestions(questions);

        return ResponseEntity.status(HttpStatus.CREATED).body(certificationRepository.save(certificationEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCertification(@PathVariable(value = "id") UUID id, @RequestBody @Valid CertificationCreateDTO certificationDto){
        Optional<CertificationEntity> certification = certificationRepository.findById(id);
        if(certification.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certification Not Found");
        }
        CertificationEntity certificationEntity = certification.get();
        List<QuestionEntity> questions = questionRepository.findAllById(certificationDto.getQuestionsId());
        certificationEntity.setCertificationQuestions(questions);

        certificationEntity = certificationRepository.save(certificationEntity);
        return ResponseEntity.status(HttpStatus.OK).body(convertCertificationToDto(certificationEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCertification(@PathVariable(value = "id") UUID id){
        Optional<CertificationEntity> certification = certificationRepository.findById(id);
        if(certification.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Certification Not Found");
        }
        certificationRepository.delete(certification.get());
        return ResponseEntity.ok().body("Certification deleted with success!");
    }      
}
