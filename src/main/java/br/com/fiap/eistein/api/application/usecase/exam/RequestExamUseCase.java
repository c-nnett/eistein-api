package br.com.fiap.eistein.api.application.usecase.exam;

import br.com.fiap.eistein.api.application.command.RequestExamCommand;
import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.DomainValidations;
import br.com.fiap.eistein.api.domain.model.Encounter;
import br.com.fiap.eistein.api.domain.model.Exam;
import br.com.fiap.eistein.api.domain.model.HealthcareProfessional;
import br.com.fiap.eistein.api.domain.repository.EncounterRepository;
import br.com.fiap.eistein.api.domain.repository.ExamRepository;
import br.com.fiap.eistein.api.domain.repository.HealthcareProfessionalRepository;
import br.com.fiap.eistein.api.domain.service.ExamProtocolNumberGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

public class RequestExamUseCase {

    private final EncounterRepository encounterRepository;
    private final HealthcareProfessionalRepository healthcareProfessionalRepository;
    private final ExamRepository examRepository;
    private final ExamProtocolNumberGenerator examProtocolNumberGenerator;

    public RequestExamUseCase(
            EncounterRepository encounterRepository,
            HealthcareProfessionalRepository healthcareProfessionalRepository,
            ExamRepository examRepository,
            ExamProtocolNumberGenerator examProtocolNumberGenerator) {
        this.encounterRepository = encounterRepository;
        this.healthcareProfessionalRepository = healthcareProfessionalRepository;
        this.examRepository = examRepository;
        this.examProtocolNumberGenerator = examProtocolNumberGenerator;
    }

    public Exam execute(RequestExamCommand command) {
        UUID requestingProfessionalIdentifier = DomainValidations.requireValue(
                command.requestingProfessionalIdentifier(), "requestingProfessionalIdentifier");

        Encounter encounter = encounterRepository.findByIdentifier(command.encounterIdentifier())
                .orElseThrow(() -> new ResourceNotFoundException("Encounter", command.encounterIdentifier()));
        HealthcareProfessional requestingProfessional = healthcareProfessionalRepository
                .findByIdentifier(requestingProfessionalIdentifier)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "HealthcareProfessional", requestingProfessionalIdentifier));

        Exam exam = Exam.request(
                examProtocolNumberGenerator.generate(),
                encounter.identifier(),
                encounter.patientIdentifier(),
                requestingProfessional.identifier(),
                command.examType(),
                command.sigtapCode(),
                command.clinicalJustification(),
                command.priority(),
                requestingProfessional.qualifiedRegistration(),
                LocalDateTime.now());
        return examRepository.save(exam);
    }
}
