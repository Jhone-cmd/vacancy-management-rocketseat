package br.com.jhonecmd.vacancy_management.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.vacancy_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.useCases.AuthenticateCandidateUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/candidates")
public class AuthCandidateController {

    @Autowired
    private AuthenticateCandidateUseCase authenticateCandidateUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> create(@RequestBody AuthCandidateDTO authCandidateDTO) {
        try {
            var result = this.authenticateCandidateUseCase.execute(authCandidateDTO);
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
