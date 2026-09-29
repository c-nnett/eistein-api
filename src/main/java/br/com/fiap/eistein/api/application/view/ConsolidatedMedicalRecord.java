package br.com.fiap.eistein.api.application.view;

import br.com.fiap.eistein.api.domain.model.Encounter;
import br.com.fiap.eistein.api.domain.model.Exam;
import br.com.fiap.eistein.api.domain.model.MedicalRecord;
import br.com.fiap.eistein.api.domain.model.Patient;

import java.util.List;

public record ConsolidatedMedicalRecord(
        Patient patient,
        MedicalRecord medicalRecord,
        List<Encounter> recentEncounters,
        List<Exam> ongoingExams) {
}
