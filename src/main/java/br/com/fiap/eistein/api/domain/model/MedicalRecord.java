package br.com.fiap.eistein.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record MedicalRecord(UUID identifier, UUID patientIdentifier, LocalDateTime createdAt) {

    public MedicalRecord {
        identifier = DomainValidations.requireValue(identifier, "identifier");
        patientIdentifier = DomainValidations.requireValue(patientIdentifier, "patientIdentifier");
        createdAt = DomainValidations.requireValue(createdAt, "createdAt");
    }

    public static MedicalRecord openFor(UUID patientIdentifier, LocalDateTime creationInstant) {
        return new MedicalRecord(UUID.randomUUID(), patientIdentifier, creationInstant);
    }
}
