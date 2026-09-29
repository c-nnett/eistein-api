package br.com.fiap.eistein.api.domain.model;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public record ExamStatusHistoryEntry(
        UUID identifier,
        ExamStatus previousStatus,
        ExamStatus newStatus,
        LocalDateTime occurredAt,
        String responsibleIdentification) {

    public ExamStatusHistoryEntry {
        identifier = DomainValidations.requireValue(identifier, "identifier");
        newStatus = DomainValidations.requireValue(newStatus, "newStatus");
        occurredAt = DomainValidations.requireValue(occurredAt, "occurredAt");
        responsibleIdentification = DomainValidations.requireText(
                responsibleIdentification, "responsibleIdentification");
    }

    public static ExamStatusHistoryEntry of(
            ExamStatus previousStatus,
            ExamStatus newStatus,
            LocalDateTime occurredAt,
            String responsibleIdentification) {
        return new ExamStatusHistoryEntry(
                UUID.randomUUID(), previousStatus, newStatus, occurredAt, responsibleIdentification);
    }

    public Optional<ExamStatus> optionalPreviousStatus() {
        return Optional.ofNullable(previousStatus);
    }
}
