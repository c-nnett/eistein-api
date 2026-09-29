package br.com.fiap.eistein.api.application.usecase.encounter;

import br.com.fiap.eistein.api.application.command.OpenEncounterCommand;
import br.com.fiap.eistein.api.domain.exception.ResourceNotFoundException;
import br.com.fiap.eistein.api.domain.model.DomainValidations;
import br.com.fiap.eistein.api.domain.model.Encounter;
import br.com.fiap.eistein.api.domain.model.MedicalRecord;
import br.com.fiap.eistein.api.domain.repository.EncounterRepository;
import br.com.fiap.eistein.api.domain.repository.HealthcareProfessionalRepository;
import br.com.fiap.eistein.api.domain.repository.MedicalRecordRepository;
import br.com.fiap.eistein.api.domain.repository.PatientRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class OpenEncounterUseCase {

    private final PatientRepository patientRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final HealthcareProfessionalRepository healthcareProfessionalRepository;
    private final EncounterRepository encounterRepository;

    public OpenEncounterUseCase(
            PatientRepository patientRepository,
            MedicalRecordRepository medicalRecordRepository,
            HealthcareProfessionalRepository healthcareProfessionalRepository,
            EncounterRepository encounterRepository) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.healthcareProfessionalRepository = healthcareProfessionalRepository;
        this.encounterRepository = encounterRepository;
    }

    public Encounter execute(OpenEncounterCommand command) {
        UUID patientIdentifier =
                DomainValidations.requireValue(command.patientIdentifier(), "patientIdentifier");
        UUID attendingProfessionalIdentifier = DomainValidations.requireValue(
                command.attendingProfessionalIdentifier(), "attendingProfessionalIdentifier");

        if (patientRepository.findByIdentifier(patientIdentifier).isEmpty()) {
            throw new ResourceNotFoundException("Patient", patientIdentifier);
        }
        if (healthcareProfessionalRepository.findByIdentifier(attendingProfessionalIdentifier).isEmpty()) {
            throw new ResourceNotFoundException("HealthcareProfessional", attendingProfessionalIdentifier);
        }
        MedicalRecord medicalRecord = medicalRecordRepository.findByPatientIdentifier(patientIdentifier)
                .orElseThrow(() -> new ResourceNotFoundException("MedicalRecord", patientIdentifier));

        Encounter encounter = Encounter.open(
                medicalRecord.identifier(),
                patientIdentifier,
                attendingProfessionalIdentifier,
                command.healthcareFacility(),
                command.occurredAt() == null ? LocalDateTime.now() : command.occurredAt(),
                command.admissionReason(),
                command.riskClassification());
        return encounterRepository.save(encounter);
    }
}
