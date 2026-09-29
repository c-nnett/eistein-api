package br.com.fiap.eistein.api.infrastructure.web.controller;

import br.com.fiap.eistein.api.application.usecase.professional.FindHealthcareProfessionalByIdentifierUseCase;
import br.com.fiap.eistein.api.application.usecase.professional.RegisterHealthcareProfessionalUseCase;
import br.com.fiap.eistein.api.infrastructure.web.mapper.HealthcareProfessionalResponseMapper;
import br.com.fiap.eistein.api.infrastructure.web.request.RegisterHealthcareProfessionalRequest;
import br.com.fiap.eistein.api.infrastructure.web.response.HealthcareProfessionalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/profissionais")
public class HealthcareProfessionalController {

    private final RegisterHealthcareProfessionalUseCase registerHealthcareProfessionalUseCase;
    private final FindHealthcareProfessionalByIdentifierUseCase findHealthcareProfessionalByIdentifierUseCase;

    public HealthcareProfessionalController(
            RegisterHealthcareProfessionalUseCase registerHealthcareProfessionalUseCase,
            FindHealthcareProfessionalByIdentifierUseCase findHealthcareProfessionalByIdentifierUseCase) {
        this.registerHealthcareProfessionalUseCase = registerHealthcareProfessionalUseCase;
        this.findHealthcareProfessionalByIdentifierUseCase = findHealthcareProfessionalByIdentifierUseCase;
    }

    @PostMapping
    public ResponseEntity<HealthcareProfessionalResponse> registerHealthcareProfessional(
            @RequestBody RegisterHealthcareProfessionalRequest request,
            UriComponentsBuilder uriComponentsBuilder) {
        HealthcareProfessionalResponse response = HealthcareProfessionalResponseMapper.toResponse(
                registerHealthcareProfessionalUseCase.execute(request.toCommand()));
        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/v1/profissionais/{id}").build(response.identifier()))
                .body(response);
    }

    @GetMapping("/{professionalIdentifier}")
    public HealthcareProfessionalResponse findHealthcareProfessionalByIdentifier(
            @PathVariable UUID professionalIdentifier) {
        return HealthcareProfessionalResponseMapper.toResponse(
                findHealthcareProfessionalByIdentifierUseCase.execute(professionalIdentifier));
    }
}
