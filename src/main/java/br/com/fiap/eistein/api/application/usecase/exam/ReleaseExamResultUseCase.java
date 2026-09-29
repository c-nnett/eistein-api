package br.com.fiap.eistein.api.application.usecase.exam;

import br.com.fiap.eistein.api.application.command.ReleaseExamResultCommand;
import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.Exam;
import br.com.fiap.eistein.api.domain.repository.ExamRepository;

import java.time.LocalDateTime;

public class ReleaseExamResultUseCase {

    private final ExamRepository examRepository;

    public ReleaseExamResultUseCase(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Exam execute(ReleaseExamResultCommand command) {
        Exam exam = examRepository.findByIdentifier(command.examIdentifier())
                .orElseThrow(() -> new ResourceNotFoundException("Exam", command.examIdentifier()));

        exam.releaseResult(
                command.resultDescription(),
                command.clinicalReportUrl(),
                command.responsibleIdentification(),
                LocalDateTime.now());
        return examRepository.save(exam);
    }
}
