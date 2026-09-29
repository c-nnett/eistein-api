package br.com.fiap.eistein.api.domain.exception;

public class ResourceNotFoundException extends DomainException {

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super("%s not found for identifier %s".formatted(resourceName, identifier));
    }
}
