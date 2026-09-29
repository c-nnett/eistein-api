package br.com.fiap.eistein.api.infrastructure.web.response;

import br.com.fiap.eistein.api.domain.model.ExamStatus;

import java.time.LocalDateTime;

public record ExamStatusHistoryEntryResponse(
        ExamStatus previousStatus,
        ExamStatus newStatus,
        LocalDateTime occurredAt,
        String responsibleIdentification) {
}
