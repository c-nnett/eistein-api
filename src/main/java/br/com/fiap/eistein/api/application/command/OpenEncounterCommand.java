package br.com.fiap.eistein.api.application.command;

import br.com.fiap.eistein.api.domain.model.RiskClassification;

import java.time.LocalDateTime;
import java.util.UUID;

public record OpenEncounterCommand(
        UUID patientIdentifier,
        UUID attendingProfessionalIdentifier,
        String healthcareFacility,
        LocalDateTime occurredAt,
        String admissionReason,
        RiskClassification riskClassification) {
}
