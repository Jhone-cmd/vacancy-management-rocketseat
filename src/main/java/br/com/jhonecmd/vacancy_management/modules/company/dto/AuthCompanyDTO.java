package br.com.jhonecmd.vacancy_management.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {
    private String email;
    private String password;
}
