package com.vidal.nlw_certification.modules.questions.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidal.nlw_certification.modules.questions.dtos.QuestionAlternativeCreateDTO;
import com.vidal.nlw_certification.modules.questions.dtos.QuestionCreateDTO;
import com.vidal.nlw_certification.modules.questions.dtos.QuestionShowDTO;
import com.vidal.nlw_certification.modules.questions.entities.QuestionEntity;
import com.vidal.nlw_certification.modules.questions.repositories.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionRepository questionRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetOneQuestionWithStatus200() throws Exception {
        // ASSERTION
        UUID id = UUID.fromString("c5f02721-6dc3-4fa6-b46d-6f2e8dca9c66");
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(id);
        questionEntity.setTechnology("Tests");
        questionEntity.setDescription("Question Description?");

        when(questionRepository.findById(id)).thenReturn(Optional.of(questionEntity));

        // ACT + ASSERT
        mockMvc.perform(get("/questions/{id}", questionEntity.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(questionEntity.getId().toString()))
                .andExpect(jsonPath("$.technology").value(questionEntity.getTechnology()))
                .andExpect(jsonPath("$.description").value(questionEntity.getDescription()))
                .andDo(print());
    }

    @Test
    void shouldCreateQuestion() throws Exception {
        QuestionCreateDTO dto = new QuestionCreateDTO(
                "Tests",
                "Any Question?",
                new ArrayList<>(Arrays.asList(
                        new QuestionAlternativeCreateDTO("alternative 1", false),
                        new QuestionAlternativeCreateDTO("alternative 2", false),
                        new QuestionAlternativeCreateDTO("alternative 3", true),
                        new QuestionAlternativeCreateDTO("alternative 4", false)
                ))
        );
        UUID id = UUID.fromString("c5f02721-6dc3-4fa6-b46d-6f2e8dca9c66");
        QuestionEntity questionEntity = new QuestionEntity(dto);
        questionEntity.setId(id);

        when(questionRepository.save(questionEntity)).thenReturn(questionEntity);

        String json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void shouldNotFoundQuestionWithStatus404() throws Exception {
        // ASSERTION
        UUID id = UUID.fromString("c5f02721-6dc3-4fa6-b46d-6f2e8dca9c69");
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(id);

        when(questionRepository.findById(id)).thenReturn(Optional.empty());

        // ACT + ASSERT
        mockMvc.perform(get("/questions/{id}", questionEntity.getId()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void shouldReturnListOfQuestionsWithStatus200() throws Exception {
        UUID id = UUID.fromString("c5f02721-6dc3-4fa6-b46d-6f2e8dca9c66");
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setId(id);
        questionEntity.setTechnology("Tests");
        questionEntity.setDescription("Question Description?");

        List<QuestionEntity> questions = new ArrayList<>();
        questions.add(questionEntity);
        questions.add(questionEntity);
        questions.add(questionEntity);

        when(questionRepository.findAll()).thenReturn(questions);
        mockMvc.perform(get("/questions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(questions.size()))
                .andDo(print());
    }
}