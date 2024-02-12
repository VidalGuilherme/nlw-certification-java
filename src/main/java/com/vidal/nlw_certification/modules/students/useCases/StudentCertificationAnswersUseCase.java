package com.vidal.nlw_certification.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidal.nlw_certification.modules.certifications.entities.CertificationEntity;
import com.vidal.nlw_certification.modules.certifications.repositories.CertificationRepository;
import com.vidal.nlw_certification.modules.questions.entities.QuestionAlternativeEntity;
import com.vidal.nlw_certification.modules.questions.entities.QuestionEntity;
import com.vidal.nlw_certification.modules.students.dtos.StudentCertificationAnswerDTO;
import com.vidal.nlw_certification.modules.students.dtos.StudentHasCertificationDTO;
import com.vidal.nlw_certification.modules.students.dtos.StudentQuestionAnswerDTO;
import com.vidal.nlw_certification.modules.students.entities.StudentCertificationAnswerEntity;
import com.vidal.nlw_certification.modules.students.entities.StudentCertificationEntity;
import com.vidal.nlw_certification.modules.students.entities.StudentEntity;
import com.vidal.nlw_certification.modules.students.repositories.StudentCertificationRepository;
import com.vidal.nlw_certification.modules.students.repositories.StudentRepository;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private StudentCertificationRepository studentCertificationRepository;

    @Autowired
    private StudentHasCertificationUseCase studentHasCertificationUseCase;

    public StudentCertificationEntity execute(StudentCertificationAnswerDTO dto) throws Exception {

        StudentHasCertificationDTO hasDto = StudentHasCertificationDTO.builder()
            .email(dto.getEmail())
            .technology(dto.getTechnology())
            .build();

        Boolean hasCertification = this.studentHasCertificationUseCase.execute(hasDto);

        if (hasCertification) {
            throw new Exception("Aluno já possui certificação "+ hasDto.getTechnology()+". Parabéns!");
        }

        // Buscar as alternativas das perguntas
        // - Correct ou Incorreta
        CertificationEntity certificationTechnology = this.certificationRepository.findByTechnology(dto.getTechnology()).getFirst();
        
        if(certificationTechnology == null){
            throw new Exception("Nenhuma certificação para a Tecnologia "+dto.getTechnology()+" foi encontrada.");
        }

        CertificationEntity certification = certificationTechnology;
        List<QuestionEntity> questionsEntity = certification.getCertificationQuestions();

        if(questionsEntity.isEmpty()){
            throw new Exception("Nenhuma certificação para a Tecnologia "+dto.getTechnology()+" foi encontrada.");
        }

        List<StudentCertificationAnswerEntity> answersCertification = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        questionsEntity.stream().forEach(certificationQuestion -> {
            QuestionAlternativeEntity correct = certificationQuestion.getAlternatives()
                .stream()
                .filter(alternative -> alternative.isCorrect())
                .findFirst()
                .get();

            StudentQuestionAnswerDTO studentQuestionAnswer = dto.getQuestionsAnswers()
                .stream()
                .filter(q -> q.getQuestionId().equals(certificationQuestion.getId()))
                .findFirst()
                .get(); 

            QuestionAlternativeEntity studentAlternative = certificationQuestion.getAlternatives()
                .stream()
                .filter(alternative -> alternative.getId().equals(studentQuestionAnswer.getAlternativeId()))
                .findFirst()
                .get();

            if(correct.getId().equals(studentAlternative.getId())){
                studentQuestionAnswer.setCorrect(true);
                correctAnswers.incrementAndGet();
            }else{
                studentQuestionAnswer.setCorrect(false);
            }
            
            StudentCertificationAnswerEntity studentCertificationAnswer = StudentCertificationAnswerEntity.builder()
                .question(certificationQuestion)
                .answer(studentAlternative)
                .build();

            
            answersCertification.add(studentCertificationAnswer);
        });        

        // Verificar se existe student pelo email
        Optional<StudentEntity> result = studentRepository.findByEmail(dto.getEmail());
        StudentEntity student;
        if (result.isEmpty()) {
            student = StudentEntity.builder().email(dto.getEmail()).build();
            student = studentRepository.save(student);
        } else {
            student = result.get();
        }

        StudentCertificationEntity studentCertificationEntity = StudentCertificationEntity.builder()
                .certification(certification)
                .student(student)
                .grade(correctAnswers.get())
                .build();

        studentCertificationEntity = studentCertificationRepository.save(studentCertificationEntity);

        for(StudentCertificationAnswerEntity answerCertification : answersCertification ){
            answerCertification.setStudentCertificationEntity(studentCertificationEntity);
        }

        studentCertificationEntity.setStudentCertificationAnswers(answersCertification);
        studentCertificationRepository.save(studentCertificationEntity);

        return studentCertificationEntity;
    }
}