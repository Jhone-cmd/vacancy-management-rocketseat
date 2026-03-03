package br.com.jhonecmd.vacancy_management.modules.candidate.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCandidateDTO {

    @NotBlank(message = "Name is required.")
    private String name;

    @Email(message = "The email field is invalid.")
    private String email;

    @Length(min = 10, max = 100, message = "The password length must be between 10 and 100 characters.")
    private String password;

    private String description;

}
