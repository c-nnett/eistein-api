package br.com.fiap.eistein.api.application.usecase.patient;

import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.Patient;
import br.com.fiap.eistein.api.domain.model.PatientDocument;
import br.com.fiap.eistein.api.domain.repository.PatientRepository;

public class FindPatientByDocumentUseCase {

    private final PatientRepository patientRepository;

    public FindPatientByDocumentUseCase(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient execute(String rawDocument) {
        PatientDocument document = PatientDocument.parse(rawDocument);
        return patientRepository.findByDocument(document)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", document.value()));
    }
}
