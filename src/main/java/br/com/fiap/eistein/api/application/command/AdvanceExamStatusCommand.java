package br.com.fiap.eistein.api.application.command;

import br.com.fiap.eistein.api.domain.model.ExamStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record AdvanceExamStatusCommand(
        UUID examIdentifier,
        ExamStatus targetStatus,
        String responsibleIdentification,
        String executingHealthcareFacility,
        LocalDateTime scheduledFor,
        LocalDateTime collectedAt,
        LocalDate estimatedResultDate) {
}
