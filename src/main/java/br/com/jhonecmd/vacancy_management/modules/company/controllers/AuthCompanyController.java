package br.com.jhonecmd.vacancy_management.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.jhonecmd.vacancy_management.modules.company.useCases.AuthenticateCompanyUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/companies")
public class AuthCompanyController {

    @Autowired
    private AuthenticateCompanyUseCase authenticateCompanyUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO) {
        try {
            var result = authenticateCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
