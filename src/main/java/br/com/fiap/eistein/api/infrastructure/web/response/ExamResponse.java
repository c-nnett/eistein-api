package br.com.fiap.eistein.api.infrastructure.web.response;

import br.com.fiap.eistein.api.domain.model.ExamPriority;
import br.com.fiap.eistein.api.domain.model.ExamStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record ExamResponse(
        UUID identifier,
        String protocolNumber,
        UUID encounterIdentifier,
        UUID patientIdentifier,
        UUID requestingProfessionalIdentifier,
        String examType,
        String sigtapCode,
        String clinicalJustification,
        ExamPriority priority,
        ExamStatus currentStatus,
        Set<ExamStatus> allowedNextStatuses,
        String executingHealthcareFacility,
        LocalDateTime requestedAt,
        LocalDateTime scheduledFor,
        LocalDateTime collectedAt,
        LocalDate estimatedResultDate,
        ExamResultResponse result,
        List<ExamStatusHistoryEntryResponse> statusHistory) {
}
