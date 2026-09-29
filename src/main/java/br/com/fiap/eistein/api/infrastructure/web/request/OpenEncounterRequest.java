package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.OpenEncounterCommand;
import br.com.fiap.eistein.api.domain.model.RiskClassification;

import java.time.LocalDateTime;
import java.util.UUID;

public record OpenEncounterRequest(
        UUID patientIdentifier,
        UUID attendingProfessionalIdentifier,
        String healthcareFacility,
        LocalDateTime occurredAt,
        String admissionReason,
        RiskClassification riskClassification) {

    public OpenEncounterCommand toCommand() {
        return new OpenEncounterCommand(
                patientIdentifier,
                attendingProfessionalIdentifier,
                healthcareFacility,
                occurredAt,
                admissionReason,
                riskClassification);
    }
}
