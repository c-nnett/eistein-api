package br.com.fiap.eistein.api.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record Encounter(
        UUID identifier,
        UUID medicalRecordIdentifier,
        UUID patientIdentifier,
        UUID attendingProfessionalIdentifier,
        String healthcareFacility,
        LocalDateTime occurredAt,
        String admissionReason,
        RiskClassification riskClassification,
        List<ClinicalNote> clinicalNotes,
        EncounterOutcome outcome) {

    public Encounter {
        identifier = DomainValidations.requireValue(identifier, "identifier");
        medicalRecordIdentifier = DomainValidations.requireValue(medicalRecordIdentifier, "medicalRecordIdentifier");
        patientIdentifier = DomainValidations.requireValue(patientIdentifier, "patientIdentifier");
        attendingProfessionalIdentifier = DomainValidations.requireValue(
                attendingProfessionalIdentifier, "attendingProfessionalIdentifier");
        healthcareFacility = DomainValidations.requireText(healthcareFacility, "healthcareFacility");
        occurredAt = DomainValidations.requireValue(occurredAt, "occurredAt");
        admissionReason = DomainValidations.requireText(admissionReason, "admissionReason");
        riskClassification = DomainValidations.requireValue(riskClassification, "riskClassification");
        clinicalNotes = clinicalNotes == null ? List.of() : List.copyOf(clinicalNotes);
    }

    public static Encounter open(
            UUID medicalRecordIdentifier,
            UUID patientIdentifier,
            UUID attendingProfessionalIdentifier,
            String healthcareFacility,
            LocalDateTime occurredAt,
            String admissionReason,
            RiskClassification riskClassification) {
        return new Encounter(
                UUID.randomUUID(),
                medicalRecordIdentifier,
                patientIdentifier,
                attendingProfessionalIdentifier,
                healthcareFacility,
                occurredAt,
                admissionReason,
                riskClassification,
                List.of(),
                null);
    }

    public Encounter withAdditionalClinicalNote(ClinicalNote clinicalNote) {
        List<ClinicalNote> updatedNotes = new ArrayList<>(clinicalNotes);
        updatedNotes.add(DomainValidations.requireValue(clinicalNote, "clinicalNote"));
        return new Encounter(
                identifier,
                medicalRecordIdentifier,
                patientIdentifier,
                attendingProfessionalIdentifier,
                healthcareFacility,
                occurredAt,
                admissionReason,
                riskClassification,
                updatedNotes,
                outcome);
    }

    public Encounter withOutcome(EncounterOutcome newOutcome) {
        if (newOutcome == null) {
            return this;
        }
        return new Encounter(
                identifier,
                medicalRecordIdentifier,
                patientIdentifier,
                attendingProfessionalIdentifier,
                healthcareFacility,
                occurredAt,
                admissionReason,
                riskClassification,
                clinicalNotes,
                newOutcome);
    }

    public Optional<EncounterOutcome> optionalOutcome() {
        return Optional.ofNullable(outcome);
    }
}
