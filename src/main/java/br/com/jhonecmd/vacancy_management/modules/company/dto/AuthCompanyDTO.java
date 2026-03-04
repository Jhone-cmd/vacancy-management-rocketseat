package br.com.jhonecmd.vacancy_management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {

    @Schema(example = "javavagas@company.com")
    private String email;

    @Schema(example = "javavagas@654321")
    private String password;
}
