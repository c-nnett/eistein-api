package br.com.fiap.eistein.api.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClinicalNote(
        UUID identifier,
        UUID authoringProfessionalIdentifier,
        String clinicalEvolution,
        String diagnosis,
        String treatmentPlan,
        LocalDateTime recordedAt) {

    public ClinicalNote {
        identifier = DomainValidations.requireValue(identifier, "identifier");
        authoringProfessionalIdentifier = DomainValidations.requireValue(
                authoringProfessionalIdentifier, "authoringProfessionalIdentifier");
        clinicalEvolution = DomainValidations.requireText(clinicalEvolution, "clinicalEvolution");
        recordedAt = DomainValidations.requireValue(recordedAt, "recordedAt");
    }
}
