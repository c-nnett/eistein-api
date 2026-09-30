package br.com.fiap.eistein.api.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    public static final String PATIENTS_TAG = "Pacientes";
    public static final String PROFESSIONALS_TAG = "Profissionais de saúde";
    public static final String ENCOUNTERS_TAG = "Atendimentos";
    public static final String EXAMS_TAG = "Exames";

    @Bean
    public OpenAPI eisteinOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Eistein API")
                        .version("v1")
                        .description("""
                                Microsserviço de Prontuário Eletrônico Unificado para o SUS.

                                Centraliza o cadastro de pacientes e profissionais de saúde, os atendimentos \
                                com triagem e evolução clínica, e o acompanhamento dos exames solicitados \
                                em cada consulta.

                                Datas seguem o formato ISO-8601 (`2026-09-17` e `2026-09-17T08:00:00`). \
                                Erros são devolvidos no formato `ApiErrorResponse`."""))
                .tags(List.of(
                        new Tag().name(PATIENTS_TAG)
                                .description("Cadastro, identificação por CPF/CNS e prontuário consolidado do paciente"),
                        new Tag().name(PROFESSIONALS_TAG)
                                .description("Cadastro de profissionais de saúde vinculados a um conselho de classe"),
                        new Tag().name(ENCOUNTERS_TAG)
                                .description("Abertura de atendimentos com triagem, evoluções clínicas e pedidos de exame"),
                        new Tag().name(EXAMS_TAG)
                                .description("Acompanhamento do ciclo de vida dos exames e liberação de resultados")));
    }
}
