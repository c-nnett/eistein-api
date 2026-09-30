package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.AdvanceExamStatusCommand;
import br.com.fiap.eistein.api.domain.model.ExamStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Dados para avanço de status do exame")
public record AdvanceExamStatusRequest(
        @Schema(description = "Próximo status do exame", example = "SCHEDULED", requiredMode = Schema.RequiredMode.REQUIRED)
        ExamStatus targetStatus,
        @Schema(description = "Identificação de quem executou a mudança", example = "LAB Central", requiredMode = Schema.RequiredMode.REQUIRED)
        String responsibleIdentification,
        @Schema(description = "Unidade que executa o exame", example = "Laboratorio Municipal Zona Leste")
        String executingHealthcareFacility,
        @Schema(description = "Data e hora agendadas para o exame", example = "2026-09-17T08:00:00")
        LocalDateTime scheduledFor,
        @Schema(description = "Data e hora da coleta; usada ao mover para `COLLECTED` (padrão: momento da requisição)", example = "2026-09-17T08:14:00")
        LocalDateTime collectedAt,
        @Schema(description = "Previsão de liberação do resultado", example = "2026-09-18")
        LocalDate estimatedResultDate) {

    public AdvanceExamStatusCommand toCommand(UUID examIdentifier) {
        return new AdvanceExamStatusCommand(
                examIdentifier,
                targetStatus,
                responsibleIdentification,
                executingHealthcareFacility,
                scheduledFor,
                collectedAt,
                estimatedResultDate);
    }
}
