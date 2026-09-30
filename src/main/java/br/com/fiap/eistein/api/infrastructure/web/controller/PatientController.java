package br.com.fiap.eistein.api.infrastructure.web.controller;

import br.com.fiap.eistein.api.application.usecase.exam.ListPatientExamsUseCase;
import br.com.fiap.eistein.api.application.usecase.patient.FindPatientByDocumentUseCase;
import br.com.fiap.eistein.api.application.usecase.patient.FindPatientByIdentifierUseCase;
import br.com.fiap.eistein.api.application.usecase.patient.RegisterPatientUseCase;
import br.com.fiap.eistein.api.application.usecase.patient.RetrieveConsolidatedMedicalRecordUseCase;
import br.com.fiap.eistein.api.domain.model.ExamStatus;
import br.com.fiap.eistein.api.infrastructure.configuration.OpenApiConfiguration;
import br.com.fiap.eistein.api.infrastructure.web.mapper.ConsolidatedMedicalRecordResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.mapper.ExamResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.mapper.PatientResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.request.RegisterPatientRequest;
import br.com.fiap.eistein.api.infrastructure.web.response.ApiErrorResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.ConsolidatedMedicalRecordResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.ExamResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.PatientResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = OpenApiConfiguration.PATIENTS_TAG)
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
    @Operation(
            summary = "Cadastrar paciente",
            description = "Cadastra um paciente identificado por CPF e CNS e abre o seu prontuário eletrônico.")
    @ApiResponse(responseCode = "201", description = "Paciente cadastrado; o header `Location` aponta para o recurso criado")
    @ApiResponse(responseCode = "400", description = "Dados inválidos (CPF, CNS ou campos obrigatórios)",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Já existe paciente cadastrado com o mesmo CPF ou CNS",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ResponseEntity<PatientResponse> registerPatient(
            @RequestBody RegisterPatientRequest request, UriComponentsBuilder uriComponentsBuilder) {
        PatientResponse response =
                PatientResponseMapper.toResponse(registerPatientUseCase.execute(request.toCommand()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/v1/pacientes/{id}").build(response.identifier()))
                .body(response);
    }

    @GetMapping
    @Operation(
            summary = "Identificar paciente por CPF ou CNS",
            description = "Localiza o paciente a partir do CPF (11 dígitos) ou do CNS (15 dígitos), "
                    + "com ou sem pontuação.")
    @ApiResponse(responseCode = "200", description = "Paciente encontrado")
    @ApiResponse(responseCode = "400", description = "Documento em formato inválido",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "Nenhum paciente cadastrado com o documento informado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public PatientResponse findPatientByDocument(
            @Parameter(description = "CPF ou CNS do paciente", example = "529.982.247-25")
            @RequestParam("documento") String document) {
        return PatientResponseMapper.toResponse(findPatientByDocumentUseCase.execute(document));
    }

    @GetMapping("/{patientIdentifier}")
    @Operation(summary = "Consultar paciente por identificador")
    @ApiResponse(responseCode = "200", description = "Paciente encontrado")
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public PatientResponse findPatientByIdentifier(
            @Parameter(description = "Identificador do paciente") @PathVariable UUID patientIdentifier) {
        return PatientResponseMapper.toResponse(findPatientByIdentifierUseCase.execute(patientIdentifier));
    }

    @GetMapping("/{patientIdentifier}/prontuario")
    @Operation(
            summary = "Consultar prontuário consolidado",
            description = "Retorna os dados do paciente, seus atendimentos do mais recente para o mais antigo "
                    + "e os exames ainda em andamento.")
    @ApiResponse(responseCode = "200", description = "Prontuário consolidado do paciente")
    @ApiResponse(responseCode = "404", description = "Paciente ou prontuário não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public ConsolidatedMedicalRecordResponse retrieveConsolidatedMedicalRecord(
            @Parameter(description = "Identificador do paciente") @PathVariable UUID patientIdentifier) {
        return ConsolidatedMedicalRecordResponseMapper.toResponse(
                retrieveConsolidatedMedicalRecordUseCase.execute(patientIdentifier));
    }

    @GetMapping("/{patientIdentifier}/exames")
    @Operation(
            summary = "Listar exames do paciente",
            description = "Painel de acompanhamento de todos os exames do paciente, opcionalmente filtrado por status.")
    @ApiResponse(responseCode = "200", description = "Exames do paciente")
    @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    public List<ExamResponse> listPatientExams(
            @Parameter(description = "Identificador do paciente") @PathVariable UUID patientIdentifier,
            @Parameter(description = "Filtra os exames pelo status atual")
            @RequestParam(value = "status", required = false) ExamStatus status) {
        return listPatientExamsUseCase.execute(patientIdentifier, status).stream()
                .map(ExamResponseMapper::toResponse)
                .toList();
    }
}
