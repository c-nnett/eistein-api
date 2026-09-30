package br.com.fiap.eistein.api.infrastructure.web.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Evolução clínica")
public record ClinicalNoteResponse(
        @Schema(description = "Identificador da evolução")
        UUID identifier,
        @Schema(description = "Identificador do profissional autor")
        UUID authoringProfessionalIdentifier,
        @Schema(description = "Descrição da evolução clínica")
        String clinicalEvolution,
        @Schema(description = "Diagnóstico")
        String diagnosis,
        @Schema(description = "Conduta / plano terapêutico")
        String treatmentPlan,
        @Schema(description = "Data e hora do registro")
        LocalDateTime recordedAt) {
}
