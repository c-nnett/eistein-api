package br.com.fiap.eistein.api.infrastructure.web.request;

import br.com.fiap.eistein.api.application.command.RegisterPatientCommand;
import br.com.fiap.eistein.api.domain.model.BiologicalSex;

import java.time.LocalDate;
import java.util.List;

public record RegisterPatientRequest(
        String fullName,
        String taxpayerIdentifier,
        String nationalHealthCardNumber,
        LocalDate birthDate,
        BiologicalSex biologicalSex,
        String phoneNumber,
        String emailAddress,
        String residentialAddress,
        List<String> allergies,
        List<String> comorbidities,
        List<String> continuousMedications) {

    public RegisterPatientCommand toCommand() {
        return new RegisterPatientCommand(
                fullName,
                taxpayerIdentifier,
                nationalHealthCardNumber,
                birthDate,
                biologicalSex,
                phoneNumber,
                emailAddress,
                residentialAddress,
                allergies,
                comorbidities,
                continuousMedications);
    }
}
