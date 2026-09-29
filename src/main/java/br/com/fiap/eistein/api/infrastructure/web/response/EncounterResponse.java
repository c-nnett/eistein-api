package br.com.fiap.eistein.api.infrastructure.web.response;

import br.com.fiap.eistein.api.domain.model.EncounterOutcome;
import br.com.fiap.eistein.api.domain.model.RiskClassification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EncounterResponse(
        UUID identifier,
        UUID medicalRecordIdentifier,
        UUID patientIdentifier,
        UUID attendingProfessionalIdentifier,
        String healthcareFacility,
        LocalDateTime occurredAt,
        String admissionReason,
        RiskClassification riskClassification,
        EncounterOutcome outcome,
        List<ClinicalNoteResponse> clinicalNotes) {
}
