package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.AdvanceExamStatusCommand;
import br.com.fiap.eistein.api.domain.model.ExamStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AdvanceExamStatusRequest(
        ExamStatus targetStatus,
        String responsibleIdentification,
        String executingHealthcareFacility,
        LocalDateTime scheduledFor,
        LocalDateTime collectedAt,
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
