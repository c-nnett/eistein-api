package br.com.fiap.eistein.api.application.usecase.exam;

import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.Exam;
import br.com.fiap.eistein.api.domain.repository.EncounterRepository;
import br.com.fiap.eistein.api.domain.repository.ExamRepository;

import java.util.List;
import java.util.UUID;

public class ListEncounterExamsUseCase {

    private final EncounterRepository encounterRepository;
    private final ExamRepository examRepository;

    public ListEncounterExamsUseCase(EncounterRepository encounterRepository, ExamRepository examRepository) {
        this.encounterRepository = encounterRepository;
        this.examRepository = examRepository;
    }

    public List<Exam> execute(UUID encounterIdentifier) {
        if (encounterRepository.findByIdentifier(encounterIdentifier).isEmpty()) {
            throw new ResourceNotFoundException("Encounter", encounterIdentifier);
        }
        return examRepository.findByEncounterIdentifier(encounterIdentifier);
    }
}
