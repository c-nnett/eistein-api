package br.com.fiap.eistein.api.infrastructure.web.response;

import br.com.fiap.eistein.api.domain.model.ExamPriority;
import br.com.fiap.eistein.api.domain.model.ExamStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Exame e sua linha do tempo")
public record ExamResponse(
        @Schema(description = "Identificador do exame")
        UUID identifier,
        @Schema(description = "Número de protocolo do exame")
        String protocolNumber,
        @Schema(description = "Identificador do atendimento em que o exame foi pedido")
        UUID encounterIdentifier,
        @Schema(description = "Identificador do paciente")
        UUID patientIdentifier,
        @Schema(description = "Identificador do profissional solicitante")
        UUID requestingProfessionalIdentifier,
        @Schema(description = "Tipo de exame")
        String examType,
        @Schema(description = "Código SIGTAP")
        String sigtapCode,
        @Schema(description = "Justificativa clínica")
        String clinicalJustification,
        @Schema(description = "Prioridade")
        ExamPriority priority,
        @Schema(description = "Status atual")
        ExamStatus currentStatus,
        @Schema(description = "Status para os quais o exame pode avançar a partir do status atual")
        Set<ExamStatus> allowedNextStatuses,
        @Schema(description = "Unidade que executa o exame")
        String executingHealthcareFacility,
        @Schema(description = "Data e hora da solicitação")
        LocalDateTime requestedAt,
        @Schema(description = "Data e hora agendadas")
        LocalDateTime scheduledFor,
        @Schema(description = "Data e hora da coleta")
        LocalDateTime collectedAt,
        @Schema(description = "Previsão de liberação do resultado")
        LocalDate estimatedResultDate,
        @Schema(description = "Resultado; nulo enquanto não liberado")
        ExamResultResponse result,
        @Schema(description = "Histórico de mudanças de status, em ordem cronológica")
        List<ExamStatusHistoryEntryResponse> statusHistory) {
}
