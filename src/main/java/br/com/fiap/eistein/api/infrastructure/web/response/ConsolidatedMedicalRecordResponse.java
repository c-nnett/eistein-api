package br.com.fiap.eistein.api.infrastructure.web.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ConsolidatedMedicalRecordResponse(
        UUID medicalRecordIdentifier,
        LocalDateTime createdAt,
        PatientResponse patient,
        List<EncounterResponse> recentEncounters,
        List<ExamResponse> ongoingExams) {
}
