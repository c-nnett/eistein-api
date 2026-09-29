package br.com.fiap.eistein.api.domain.model;

public record ContactInformation(String phoneNumber, String emailAddress, String residentialAddress) {

    public static ContactInformation empty() {
        return new ContactInformation(null, null, null);
    }
}
