package com.vidal.nlw_certification.modules.questions.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.vidal.nlw_certification.modules.questions.dtos.QuestionShowDTO;
import com.vidal.nlw_certification.modules.questions.dtos.QuestionAlternativeCreateDTO;
import com.vidal.nlw_certification.modules.questions.dtos.QuestionAlternativeShowDTO;
import com.vidal.nlw_certification.modules.questions.dtos.QuestionCreateDTO;
import com.vidal.nlw_certification.modules.questions.entities.QuestionAlternativeEntity;
import com.vidal.nlw_certification.modules.questions.entities.QuestionEntity;
import com.vidal.nlw_certification.modules.questions.repositories.QuestionRepository;

import jakarta.validation.Valid;

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

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
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

    public static QuestionShowDTO convertQuestionToDto(QuestionEntity question){

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

    public static QuestionAlternativeShowDTO convertAlternativeToDto(QuestionAlternativeEntity alternative){

        return QuestionAlternativeShowDTO.builder()
        .id(alternative.getId())
        .description(alternative.getDescription())
        .build();
    }

    private QuestionEntity convertDtoToQuestion(QuestionCreateDTO questionDTO){
        
        QuestionEntity question = QuestionEntity.builder()
            .description(questionDTO.getDescription())
            .technology(questionDTO.getTechnology())
            .build();

        for(QuestionAlternativeCreateDTO alternative : questionDTO.getAlternatives()){
            QuestionAlternativeEntity alternativeEntity = convertDtoToQuestionAlternative(alternative);
            question.addAlternative(alternativeEntity);
        }

        return question;
    }

    private QuestionAlternativeEntity convertDtoToQuestionAlternative(QuestionAlternativeCreateDTO alternativeDTO){
       
        QuestionAlternativeEntity alternativeEntity = QuestionAlternativeEntity.builder()
            .description(alternativeDTO.getDescription())
            .isCorrect(alternativeDTO.getIsCorrect())
            .build();

        return alternativeEntity;
    }

    // CRUD
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneQuestion(@PathVariable(value = "id") UUID id){
        Optional<QuestionEntity> question = questionRepository.findById(id);
        if(question.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question Not Found");
        }        
        return ResponseEntity.ok().body(convertQuestionToDto(question.get()));
    }

    @PostMapping
    public ResponseEntity<Object> createQuestion(@RequestBody @Valid QuestionCreateDTO questionDto){
        QuestionEntity questionEntity = this.convertDtoToQuestion(questionDto);        
        questionEntity = questionRepository.save(questionEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertQuestionToDto(questionEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateQuestion(@PathVariable(value = "id") UUID id, @RequestBody @Valid QuestionCreateDTO questionDto){
        Optional<QuestionEntity> question = questionRepository.findById(id);
        if(question.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question Not Found");
        }
        QuestionEntity questionEntity = question.get();
        questionEntity.setDescription(questionDto.getDescription());
        questionEntity.setTechnology(questionDto.getTechnology());

        List<QuestionAlternativeEntity> oldAlternatives = new ArrayList<>(); 
        oldAlternatives.addAll(questionEntity.getAlternatives());
        for(QuestionAlternativeEntity alternative : oldAlternatives){
            System.out.println(alternative.getId());
            questionEntity.removeAlternative(alternative);
        }
        
        System.out.println("TESTE");
        for(QuestionAlternativeEntity alternative : questionEntity.getAlternatives()){
            System.out.println(alternative.getId());            
        }

        for(QuestionAlternativeCreateDTO alternative : questionDto.getAlternatives()){
            QuestionAlternativeEntity alternativeEntity = convertDtoToQuestionAlternative(alternative);
            questionEntity.addAlternative(alternativeEntity);
        }

        questionEntity = this.questionRepository.save(questionEntity);
        return ResponseEntity.status(HttpStatus.OK).body(convertQuestionToDto(questionEntity));
    }   

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable(value = "id") UUID id){
        Optional<QuestionEntity> question = questionRepository.findById(id);
        if(question.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question Not Found");
        }
        questionRepository.delete(question.get());
        return ResponseEntity.ok().body("Question deleted with success!");
    }

}
