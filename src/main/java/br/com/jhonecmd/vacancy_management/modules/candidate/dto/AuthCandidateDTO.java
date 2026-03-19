package br.com.jhonecmd.vacancy_management.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCandidateDTO {

    @Schema(example = "johndoe@email.com")
    private String email;

    @Schema(example = "john@123456789")
    private String password;
}
