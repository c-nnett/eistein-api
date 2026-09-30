package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.OpenEncounterCommand;
import br.com.fiap.eistein.api.domain.model.RiskClassification;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados para abertura de atendimento")
public record OpenEncounterRequest(
        @Schema(description = "Identificador do paciente", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID patientIdentifier,
        @Schema(description = "Identificador do profissional responsável pelo atendimento", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID attendingProfessionalIdentifier,
        @Schema(description = "Unidade de saúde do atendimento", example = "UPA Centro", requiredMode = Schema.RequiredMode.REQUIRED)
        String healthcareFacility,
        @Schema(description = "Data e hora do atendimento", example = "2026-09-16T03:32:34", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime occurredAt,
        @Schema(description = "Motivo de entrada / queixa principal", example = "Dor toracica", requiredMode = Schema.RequiredMode.REQUIRED)
        String admissionReason,
        @Schema(description = "Classificação de risco da triagem", example = "VERY_URGENT", requiredMode = Schema.RequiredMode.REQUIRED)
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
