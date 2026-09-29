package br.com.fiap.eistein.api.infrastructure.web.mapper;

import br.com.fiap.eistein.api.application.view.ConsolidatedMedicalRecord;
import br.com.fiap.eistein.api.infrastructure.web.response.ConsolidatedMedicalRecordResponse;

public final class ConsolidatedMedicalRecordResponseMapper {

    private ConsolidatedMedicalRecordResponseMapper() {
    }

    public static ConsolidatedMedicalRecordResponse toResponse(ConsolidatedMedicalRecord consolidatedMedicalRecord) {
        return new ConsolidatedMedicalRecordResponse(
                consolidatedMedicalRecord.medicalRecord().identifier(),
                consolidatedMedicalRecord.medicalRecord().createdAt(),
                PatientResponseMapper.toResponse(consolidatedMedicalRecord.patient()),
                consolidatedMedicalRecord.recentEncounters().stream()
                        .map(EncounterResponseMapper::toResponse)
                        .toList(),
                consolidatedMedicalRecord.ongoingExams().stream()
                        .map(ExamResponseMapper::toResponse)
                        .toList());
    }
}
