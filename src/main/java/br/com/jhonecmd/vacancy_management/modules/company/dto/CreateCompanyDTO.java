package br.com.jhonecmd.vacancy_management.modules.company.dto;

import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyDTO {

    @NotBlank(message = "Name is required.")
    @Schema(example = "Java Vagas")
    private String name;

    @Schema(example = "javavagas@company.com")
    @Email(message = "The email field is invalid.")
    private String email;

    @Schema(example = "javavagas@654321")
    @NotBlank(message = "Name is required.")
    @Length(min = 10, max = 100, message = "The password length must be between 10 and 100 characters.")
    private String password;

    @Schema(example = "Empresa destinada a vagas JAVA")
    private String description;

    @Schema(example = "https://www.javavagas.com.br")
    private String webSite;

}
