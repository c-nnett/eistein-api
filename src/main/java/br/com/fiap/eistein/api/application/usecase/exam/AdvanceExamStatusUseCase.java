package br.com.fiap.eistein.api.application.usecase.exam;

import br.com.fiap.eistein.api.application.command.AdvanceExamStatusCommand;
import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.Exam;
import br.com.fiap.eistein.api.domain.model.ExamStatusTransition;
import br.com.fiap.eistein.api.domain.repository.ExamRepository;

import java.time.LocalDateTime;

public class AdvanceExamStatusUseCase {

    private final ExamRepository examRepository;

    public AdvanceExamStatusUseCase(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Exam execute(AdvanceExamStatusCommand command) {
        Exam exam = examRepository.findByIdentifier(command.examIdentifier())
                .orElseThrow(() -> new ResourceNotFoundException("Exam", command.examIdentifier()));

        exam.applyStatusTransition(new ExamStatusTransition(
                command.targetStatus(),
                command.responsibleIdentification(),
                command.executingHealthcareFacility(),
                command.scheduledFor(),
                command.collectedAt(),
                command.estimatedResultDate(),
                LocalDateTime.now()));
        return examRepository.save(exam);
    }
}
