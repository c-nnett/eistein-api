package br.com.fiap.eistein.api.infrastructure.web.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClinicalNoteResponse(
        UUID identifier,
        UUID authoringProfessionalIdentifier,
        String clinicalEvolution,
        String diagnosis,
        String treatmentPlan,
        LocalDateTime recordedAt) {
}
