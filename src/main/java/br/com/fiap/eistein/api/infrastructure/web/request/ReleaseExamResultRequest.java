package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.ReleaseExamResultCommand;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados para liberação de resultado de exame")
public record ReleaseExamResultRequest(
        @Schema(description = "Descrição do resultado", example = "Troponina I 0,02 ng/mL - dentro do valor de referencia", requiredMode = Schema.RequiredMode.REQUIRED)
        String resultDescription,
        @Schema(description = "URL do laudo", example = "https://laudos.sus.gov.br/EXM-20260916-000001")
        String clinicalReportUrl,
        @Schema(description = "Identificação de quem liberou o resultado", example = "Bioquimico Responsavel")
        String responsibleIdentification) {

    public ReleaseExamResultCommand toCommand(UUID examIdentifier) {
        return new ReleaseExamResultCommand(
                examIdentifier, resultDescription, clinicalReportUrl, responsibleIdentification);
    }
}
