package br.com.fiap.eistein.api.infrastructure.web.response;

import br.com.fiap.eistein.api.domain.model.EncounterOutcome;
import br.com.fiap.eistein.api.domain.model.RiskClassification;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Atendimento")
public record EncounterResponse(
        @Schema(description = "Identificador do atendimento")
        UUID identifier,
        @Schema(description = "Identificador do prontuário")
        UUID medicalRecordIdentifier,
        @Schema(description = "Identificador do paciente")
        UUID patientIdentifier,
        @Schema(description = "Identificador do profissional responsável")
        UUID attendingProfessionalIdentifier,
        @Schema(description = "Unidade de saúde do atendimento")
        String healthcareFacility,
        @Schema(description = "Data e hora do atendimento")
        LocalDateTime occurredAt,
        @Schema(description = "Motivo de entrada")
        String admissionReason,
        @Schema(description = "Classificação de risco da triagem")
        RiskClassification riskClassification,
        @Schema(description = "Desfecho do atendimento; nulo enquanto não definido")
        EncounterOutcome outcome,
        @Schema(description = "Evoluções clínicas registradas")
        List<ClinicalNoteResponse> clinicalNotes) {
}
