package br.com.fiap.eistein.api.infrastructure.web.response;

import java.time.LocalDateTime;

public record ExamResultResponse(String description, String clinicalReportUrl, LocalDateTime releasedAt) {
}
