package br.com.fiap.eistein.api.application.usecase.exam;

import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.Exam;
import br.com.fiap.eistein.api.domain.model.ExamStatus;
import br.com.fiap.eistein.api.domain.repository.ExamRepository;
import br.com.fiap.eistein.api.domain.repository.PatientRepository;

import java.util.List;
import java.util.UUID;

public class ListPatientExamsUseCase {

    private final PatientRepository patientRepository;
    private final ExamRepository examRepository;

    public ListPatientExamsUseCase(PatientRepository patientRepository, ExamRepository examRepository) {
        this.patientRepository = patientRepository;
        this.examRepository = examRepository;
    }

    public List<Exam> execute(UUID patientIdentifier, ExamStatus filteredStatus) {
        if (patientRepository.findByIdentifier(patientIdentifier).isEmpty()) {
            throw new ResourceNotFoundException("Patient", patientIdentifier);
        }
        return examRepository.findByPatientIdentifierAndOptionalStatus(patientIdentifier, filteredStatus);
    }
}
