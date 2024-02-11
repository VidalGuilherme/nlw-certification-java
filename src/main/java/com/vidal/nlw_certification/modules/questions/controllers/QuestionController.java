package com.vidal.nlw_certification.modules.questions.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.vidal.nlw_certification.modules.questions.dtos.QuestionAlternativeShowDTO;
import com.vidal.nlw_certification.modules.questions.dtos.QuestionShowDTO;
import com.vidal.nlw_certification.modules.questions.entities.QuestionAlternativeEntity;
import com.vidal.nlw_certification.modules.questions.entities.QuestionEntity;
import com.vidal.nlw_certification.modules.questions.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/")
    public ResponseEntity<List<QuestionShowDTO>> findAll() {
        List<QuestionEntity> result = this.questionRepository.findAll();
        List<QuestionShowDTO> questions = result.stream().map(question -> convertQuestionToDto(question))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(questions);
    }    

    @GetMapping("/technology/{technology}")
    public ResponseEntity<Object> findByTechnology(@PathVariable String technology) {
        List<QuestionEntity> result = this.questionRepository.findByTechnology(technology);

        List<QuestionShowDTO> questions = result.stream().map(question -> convertQuestionToDto(question))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(questions);
    }

    private QuestionShowDTO convertQuestionToDto(QuestionEntity question){

        QuestionShowDTO questionDto = QuestionShowDTO.builder()
            .id(question.getId())
            .technology(question.getTechnology())
            .description(question.getDescription())
            .build();

        List<QuestionAlternativeShowDTO> alternativesDto = question.getAlternatives().stream().map(alternative -> convertAlternativeToDto(alternative))
        .collect(Collectors.toList());
        
        questionDto.setAlternatives(alternativesDto);

        return questionDto;
    }

    private QuestionAlternativeShowDTO convertAlternativeToDto(QuestionAlternativeEntity alternative){

        return QuestionAlternativeShowDTO.builder()
        .id(alternative.getId())
        .description(alternative.getDescription())
        .build();
    }

}
