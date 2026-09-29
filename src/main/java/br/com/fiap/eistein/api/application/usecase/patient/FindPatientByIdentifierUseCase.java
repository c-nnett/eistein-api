package br.com.fiap.eistein.api.application.usecase.patient;

import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.Patient;
import br.com.fiap.eistein.api.domain.repository.PatientRepository;

import java.util.UUID;

public class FindPatientByIdentifierUseCase {

    private final PatientRepository patientRepository;

    public FindPatientByIdentifierUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient execute(UUID patientIdentifier) {
        return patientRepository.findByIdentifier(patientIdentifier)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", patientIdentifier));
    }
}
