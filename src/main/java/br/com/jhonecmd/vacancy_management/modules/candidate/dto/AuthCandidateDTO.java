package br.com.jhonecmd.vacancy_management.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthCandidateDTO {

    @Schema(example = "johndoe@email.com")
    private String email;

    @Schema(example = "john@123456789")
    private String password;

    public AuthCandidateDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
