package br.com.fiap.eistein.api.application.usecase.exam;

import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.Exam;
import br.com.fiap.eistein.api.domain.repository.ExamRepository;

import java.util.UUID;

public class FindExamByIdentifierUseCase {

    private final ExamRepository examRepository;

    public FindExamByIdentifierUseCase(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public Exam execute(UUID examIdentifier) {
        return examRepository.findByIdentifier(examIdentifier)
                .orElseThrow(() -> new ResourceNotFoundException("Exam", examIdentifier));
    }
}
