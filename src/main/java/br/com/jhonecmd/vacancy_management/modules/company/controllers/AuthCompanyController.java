package br.com.jhonecmd.vacancy_management.modules.company.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.jhonecmd.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.jhonecmd.vacancy_management.modules.company.useCases.AuthenticateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/companies")
@Tag(name = "Companies", description = "Routes intended for companies.")
public class AuthCompanyController {

    @Autowired
    private AuthenticateCompanyUseCase authenticateCompanyUseCase;

    @PostMapping("/auth")
    @Operation(summary = "Company authentication.", description = "This route is designed to company authentication.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token generated successfully.", content = {
                    @Content(schema = @Schema(implementation = AuthCandidateResponseDTO.class))
            }),

            @ApiResponse(responseCode = "401", description = "Invalid Credentials", content = {
                    @Content(schema = @Schema(implementation = String.class, example = "Invalid Credentials."))
            })
    })
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO) {
        try {
            var result = authenticateCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
