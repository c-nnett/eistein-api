package br.com.fiap.eistein.api.infrastructure.web.controller;

import br.com.fiap.eistein.api.application.usecase.encounter.FindEncounterByIdentifierUseCase;
import br.com.fiap.eistein.api.application.usecase.encounter.OpenEncounterUseCase;
import br.com.fiap.eistein.api.application.usecase.encounter.RegisterClinicalNoteUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.ListEncounterExamsUseCase;
import br.com.fiap.eistein.api.application.usecase.exam.RequestExamUseCase;
import br.com.fiap.eistein.api.infrastructure.web.mapper.EncounterResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.mapper.ExamResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.request.OpenEncounterRequest;
import br.com.fiap.eistein.api.infrastructure.web.request.RegisterClinicalNoteRequest;
import br.com.fiap.eistein.api.infrastructure.web.request.RequestExamRequest;
import br.com.fiap.eistein.api.infrastructure.web.response.EncounterResponse;
import br.com.fiap.eistein.api.infrastructure.web.response.ExamResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/atendimentos")
public class EncounterController {

    private final OpenEncounterUseCase openEncounterUseCase;
    private final FindEncounterByIdentifierUseCase findEncounterByIdentifierUseCase;
    private final RegisterClinicalNoteUseCase registerClinicalNoteUseCase;
    private final RequestExamUseCase requestExamUseCase;
    private final ListEncounterExamsUseCase listEncounterExamsUseCase;

    public EncounterController(
            OpenEncounterUseCase openEncounterUseCase,
            FindEncounterByIdentifierUseCase findEncounterByIdentifierUseCase,
            RegisterClinicalNoteUseCase registerClinicalNoteUseCase,
            RequestExamUseCase requestExamUseCase,
            ListEncounterExamsUseCase listEncounterExamsUseCase) {
        this.openEncounterUseCase = openEncounterUseCase;
        this.findEncounterByIdentifierUseCase = findEncounterByIdentifierUseCase;
        this.registerClinicalNoteUseCase = registerClinicalNoteUseCase;
        this.requestExamUseCase = requestExamUseCase;
        this.listEncounterExamsUseCase = listEncounterExamsUseCase;
    }

    @PostMapping
    public ResponseEntity<EncounterResponse> openEncounter(
            @RequestBody OpenEncounterRequest request, UriComponentsBuilder uriComponentsBuilder) {
        EncounterResponse response =
                EncounterResponseMapper.toResponse(openEncounterUseCase.execute(request.toCommand()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/v1/atendimentos/{id}").build(response.identifier()))
                .body(response);
    }

    @GetMapping("/{encounterIdentifier}")
    public EncounterResponse findEncounterByIdentifier(@PathVariable UUID encounterIdentifier) {
        return EncounterResponseMapper.toResponse(findEncounterByIdentifierUseCase.execute(encounterIdentifier));
    }

    @PostMapping("/{encounterIdentifier}/evolucoes")
    public EncounterResponse registerClinicalNote(
            @PathVariable UUID encounterIdentifier, @RequestBody RegisterClinicalNoteRequest request) {
        return EncounterResponseMapper.toResponse(
                registerClinicalNoteUseCase.execute(request.toCommand(encounterIdentifier)));
    }

    @PostMapping("/{encounterIdentifier}/exames")
    public ResponseEntity<ExamResponse> requestExam(
            @PathVariable UUID encounterIdentifier,
            @RequestBody RequestExamRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        ExamResponse response =
                ExamResponseMapper.toResponse(requestExamUseCase.execute(request.toCommand(encounterIdentifier)));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/v1/exames/{id}").build(response.identifier()))
                .body(response);
    }

    @GetMapping("/{encounterIdentifier}/exames")
    public List<ExamResponse> listEncounterExams(@PathVariable UUID encounterIdentifier) {
        return listEncounterExamsUseCase.execute(encounterIdentifier).stream()
                .map(ExamResponseMapper::toResponse)
                .toList();
    }
}
