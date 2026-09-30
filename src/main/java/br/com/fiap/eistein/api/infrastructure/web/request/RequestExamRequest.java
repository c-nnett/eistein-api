package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.RequestExamCommand;
import br.com.fiap.eistein.api.domain.model.ExamPriority;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Dados para solicitação de exame")
public record RequestExamRequest(
        @Schema(description = "Identificador do profissional solicitante", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID requestingProfessionalIdentifier,
        @Schema(description = "Tipo de exame", example = "Troponina I", requiredMode = Schema.RequiredMode.REQUIRED)
        String examType,
        @Schema(description = "Código do procedimento na tabela SIGTAP", example = "0202010627")
        String sigtapCode,
        @Schema(description = "Justificativa clínica do pedido", example = "Investigacao de sindrome coronariana aguda", requiredMode = Schema.RequiredMode.REQUIRED)
        String clinicalJustification,
        @Schema(description = "Prioridade; `ROUTINE` quando omitida", example = "URGENT")
        ExamPriority priority) {

    public RequestExamCommand toCommand(UUID encounterIdentifier) {
        return new RequestExamCommand(
                encounterIdentifier,
                requestingProfessionalIdentifier,
                examType,
                sigtapCode,
                clinicalJustification,
                priority);
    }
}
