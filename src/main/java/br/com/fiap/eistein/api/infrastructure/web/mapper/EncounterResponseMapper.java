package br.com.fiap.eistein.api.infrastructure.web.mapper;

import br.com.fiap.eistein.api.domain.model.ClinicalNote;
import br.com.fiap.eistein.api.domain.model.Encounter;
import br.com.fiap.eistein.api.infrastructure.web.response.ClinicalNoteResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.EncounterResponse;

public final class EncounterResponseMapper {

    private EncounterResponseMapper() {
    }

    public static EncounterResponse toResponse(Encounter encounter) {
        return new EncounterResponse(
                encounter.identifier(),
                encounter.medicalRecordIdentifier(),
                encounter.patientIdentifier(),
                encounter.attendingProfessionalIdentifier(),
                encounter.healthcareFacility(),
                encounter.occurredAt(),
                encounter.admissionReason(),
                encounter.riskClassification(),
                encounter.outcome(),
                encounter.clinicalNotes().stream().map(EncounterResponseMapper::toResponse).toList());
    }

    private static ClinicalNoteResponse toResponse(ClinicalNote clinicalNote) {
        return new ClinicalNoteResponse(
                clinicalNote.identifier(),
                clinicalNote.authoringProfessionalIdentifier(),
                clinicalNote.clinicalEvolution(),
                clinicalNote.diagnosis(),
                clinicalNote.treatmentPlan(),
                clinicalNote.recordedAt());
    }
}
