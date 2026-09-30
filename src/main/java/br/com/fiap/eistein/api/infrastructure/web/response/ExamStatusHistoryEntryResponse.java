package br.com.fiap.eistein.api.infrastructure.web.response;

import br.com.fiap.eistein.api.domain.model.ExamStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Entrada do histórico de status do exame")
public record ExamStatusHistoryEntryResponse(
        @Schema(description = "Status anterior; nulo na criação do exame")
        ExamStatus previousStatus,
        @Schema(description = "Novo status")
        ExamStatus newStatus,
        @Schema(description = "Data e hora da mudança")
        LocalDateTime occurredAt,
        @Schema(description = "Identificação de quem executou a mudança")
        String responsibleIdentification) {
}
