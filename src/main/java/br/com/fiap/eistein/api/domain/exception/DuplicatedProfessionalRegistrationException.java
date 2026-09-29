package br.com.fiap.eistein.api.domain.exception;

public class DuplicatedProfessionalRegistrationException extends DomainException {

    public DuplicatedProfessionalRegistrationException(String councilRegistrationNumber) {
        super("A healthcare professional is already registered with the council registration %s"
                .formatted(councilRegistrationNumber));
    }
}
