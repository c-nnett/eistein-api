package br.com.fiap.eistein.api.application.command;

import br.com.fiap.eistein.api.domain.model.BiologicalSex;

import java.time.LocalDate;
import java.util.List;

public record RegisterPatientCommand(
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
}
