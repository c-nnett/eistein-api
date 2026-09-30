package br.com.fiap.eistein.api.infrastructure.web.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "Prontuário consolidado do paciente")
public record ConsolidatedMedicalRecordResponse(
        @Schema(description = "Identificador do prontuário")
        UUID medicalRecordIdentifier,
        @Schema(description = "Data e hora de abertura do prontuário")
        LocalDateTime createdAt,
        @Schema(description = "Dados do paciente")
        PatientResponse patient,
        @Schema(description = "Atendimentos, do mais recente para o mais antigo")
        List<EncounterResponse> recentEncounters,
        @Schema(description = "Exames ainda não concluídos")
        List<ExamResponse> ongoingExams) {
}
