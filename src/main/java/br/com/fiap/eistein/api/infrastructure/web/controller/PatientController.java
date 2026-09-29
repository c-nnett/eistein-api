package br.com.fiap.eistein.api.infrastructure.web.controller;

import br.com.fiap.eistein.api.application.usecase.exam.ListPatientExamsUseCase;
import br.com.fiap.eistein.api.application.usecase.patient.FindPatientByDocumentUseCase;
import br.com.fiap.eistein.api.application.usecase.patient.FindPatientByIdentifierUseCase;
import br.com.fiap.eistein.api.application.usecase.patient.RegisterPatientUseCase;
import br.com.fiap.eistein.api.application.usecase.patient.RetrieveConsolidatedMedicalRecordUseCase;
import br.com.fiap.eistein.api.domain.model.ExamStatus;
import br.com.fiap.eistein.api.infrastructure.web.mapper.ConsolidatedMedicalRecordResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.mapper.ExamResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.mapper.PatientResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.request.RegisterPatientRequest;
import br.com.fiap.eistein.api.infrastructure.web.response.ConsolidatedMedicalRecordResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.ExamResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.PatientResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pacientes")
public class PatientController {

    private final RegisterPatientUseCase registerPatientUseCase;
    private final FindPatientByDocumentUseCase findPatientByDocumentUseCase;
    private final FindPatientByIdentifierUseCase findPatientByIdentifierUseCase;
    private final RetrieveConsolidatedMedicalRecordUseCase retrieveConsolidatedMedicalRecordUseCase;
    private final ListPatientExamsUseCase listPatientExamsUseCase;

    public PatientController(
            RegisterPatientUseCase registerPatientUseCase,
            FindPatientByDocumentUseCase findPatientByDocumentUseCase,
            FindPatientByIdentifierUseCase findPatientByIdentifierUseCase,
            RetrieveConsolidatedMedicalRecordUseCase retrieveConsolidatedMedicalRecordUseCase,
            ListPatientExamsUseCase listPatientExamsUseCase) {
        this.registerPatientUseCase = registerPatientUseCase;
        this.findPatientByDocumentUseCase = findPatientByDocumentUseCase;
        this.findPatientByIdentifierUseCase = findPatientByIdentifierUseCase;
        this.retrieveConsolidatedMedicalRecordUseCase = retrieveConsolidatedMedicalRecordUseCase;
        this.listPatientExamsUseCase = listPatientExamsUseCase;
    }

    @PostMapping
    public ResponseEntity<PatientResponse> registerPatient(
            @RequestBody RegisterPatientRequest request, UriComponentsBuilder uriComponentsBuilder) {
        PatientResponse response =
                PatientResponseMapper.toResponse(registerPatientUseCase.execute(request.toCommand()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/v1/pacientes/{id}").build(response.identifier()))
                .body(response);
    }

    @GetMapping
    public PatientResponse findPatientByDocument(@RequestParam("documento") String document) {
        return PatientResponseMapper.toResponse(findPatientByDocumentUseCase.execute(document));
    }

    @GetMapping("/{patientIdentifier}")
    public PatientResponse findPatientByIdentifier(@PathVariable UUID patientIdentifier) {
        return PatientResponseMapper.toResponse(findPatientByIdentifierUseCase.execute(patientIdentifier));
    }

    @GetMapping("/{patientIdentifier}/prontuario")
    public ConsolidatedMedicalRecordResponse retrieveConsolidatedMedicalRecord(@PathVariable UUID patientIdentifier) {
        return ConsolidatedMedicalRecordResponseMapper.toResponse(
                retrieveConsolidatedMedicalRecordUseCase.execute(patientIdentifier));
    }

    @GetMapping("/{patientIdentifier}/exames")
    public List<ExamResponse> listPatientExams(
            @PathVariable UUID patientIdentifier,
            @RequestParam(value = "status", required = false) ExamStatus status) {
        return listPatientExamsUseCase.execute(patientIdentifier, status).stream()
                .map(ExamResponseMapper::toResponse)
                .toList();
    }
}
