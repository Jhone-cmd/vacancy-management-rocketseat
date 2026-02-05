package br.com.jhonecmd.vacancy_management.modules.candidate.entities;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class CandidateEntity {

    private UUID id;
    private String name;

    @Email(message = "The email field is invalid.")
    private String email;

    @Length(min = 10, max = 100)
    private String password;

    private String description;
    private String curriculum;

}
