package br.com.fiap.eistein.api.infrastructure.web.mapper;

import br.com.fiap.eistein.api.domain.model.Exam;
import br.com.fiap.eistein.api.domain.model.ExamStatusHistoryEntry;
import br.com.fiap.eistein.api.infrastructure.web.response.ExamResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.ExamResultResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.ExamStatusHistoryEntryResponse;

public final class ExamResponseMapper {

    private ExamResponseMapper() {
    }

    public static ExamResponse toResponse(Exam exam) {
        return new ExamResponse(
                exam.identifier(),
                exam.protocolNumber(),
                exam.encounterIdentifier(),
                exam.patientIdentifier(),
                exam.requestingProfessionalIdentifier(),
                exam.examType(),
                exam.sigtapCode(),
                exam.clinicalJustification(),
                exam.priority(),
                exam.currentStatus(),
                exam.currentStatus().allowedTransitions(),
                exam.executingHealthcareFacility(),
                exam.requestedAt(),
                exam.scheduledFor(),
                exam.collectedAt(),
                exam.estimatedResultDate(),
                exam.optionalResult()
                        .map(result -> new ExamResultResponse(
                                result.description(), result.clinicalReportUrl(), result.releasedAt()))
                        .orElse(null),
                exam.statusHistory().stream().map(ExamResponseMapper::toResponse).toList());
    }

    private static ExamStatusHistoryEntryResponse toResponse(ExamStatusHistoryEntry historyEntry) {
        return new ExamStatusHistoryEntryResponse(
                historyEntry.previousStatus(),
                historyEntry.newStatus(),
                historyEntry.occurredAt(),
                historyEntry.responsibleIdentification());
    }
}
