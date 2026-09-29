package br.com.fiap.eistein.api.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExamStatusTransition(
        ExamStatus targetStatus,
        String responsibleIdentification,
        String executingHealthcareFacility,
        LocalDateTime scheduledFor,
        LocalDateTime collectedAt,
        LocalDate estimatedResultDate,
        LocalDateTime occurredAt) {

    public ExamStatusTransition {
        targetStatus = DomainValidations.requireValue(targetStatus, "targetStatus");
        responsibleIdentification = DomainValidations.requireText(
                responsibleIdentification, "responsibleIdentification");
        occurredAt = occurredAt == null ? LocalDateTime.now() : occurredAt;
    }
}
