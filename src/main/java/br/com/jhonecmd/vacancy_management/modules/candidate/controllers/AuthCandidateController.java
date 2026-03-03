package br.com.jhonecmd.vacancy_management.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.vacancy_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.useCases.AuthenticateCandidateUseCase;
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
@RequestMapping("/candidates")
@Tag(name = "Candidates", description = "Routes intended for candidates.")
public class AuthCandidateController {

    @Autowired
    private AuthenticateCandidateUseCase authenticateCandidateUseCase;

    @PostMapping("/auth")
    @Operation(summary = "Candidate authentication.", description = "This route is designed to candidate authentication.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token generated successfully.", content = {
                    @Content(schema = @Schema(implementation = AuthCandidateResponseDTO.class))
            }),

            @ApiResponse(responseCode = "401", description = "Invalid Credentials", content = {
                    @Content(schema = @Schema(implementation = String.class, example = "Invalid Credentials."))
            })
    })
    public ResponseEntity<Object> create(@RequestBody AuthCandidateDTO authCandidateDTO) {
        try {
            var result = this.authenticateCandidateUseCase.execute(authCandidateDTO);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
