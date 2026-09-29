package br.com.fiap.eistein.api.domain.model;

import java.time.LocalDateTime;

public record ExamResult(String description, String clinicalReportUrl, LocalDateTime releasedAt) {

    public ExamResult {
        description = DomainValidations.requireText(description, "description");
        releasedAt = DomainValidations.requireValue(releasedAt, "releasedAt");
    }
}
