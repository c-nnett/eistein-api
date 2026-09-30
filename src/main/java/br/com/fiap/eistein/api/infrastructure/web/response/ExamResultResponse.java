package br.com.fiap.eistein.api.infrastructure.web.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Resultado de exame")
public record ExamResultResponse(
        @Schema(description = "Descrição do resultado") String description,
        @Schema(description = "URL do laudo") String clinicalReportUrl,
        @Schema(description = "Data e hora da liberação") LocalDateTime releasedAt) {
}
