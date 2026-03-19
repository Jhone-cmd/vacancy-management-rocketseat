package br.com.jhonecmd.vacancy_management.modules.candidate.dto;

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
public class CreateCandidateDTO {

    @NotBlank(message = "Name is required.")
    @Schema(example = "John Doe")
    private String name;

    @Email(message = "The email field is invalid.")
    @Schema(example = "johndoe@email.com")
    private String email;

    @Length(min = 10, max = 100, message = "The password length must be between 10 and 100 characters.")
    @Schema(example = "john@123456789")
    private String password;

    @Schema(example = "Procuro minha primeira vaga dev.")
    private String description;

}
