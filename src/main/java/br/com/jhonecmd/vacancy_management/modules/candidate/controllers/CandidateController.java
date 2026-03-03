package br.com.jhonecmd.vacancy_management.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.vacancy_management.exceptions.ErrorMessageDTO;
import br.com.jhonecmd.vacancy_management.exceptions.ResourceAlreadyExists;
import br.com.jhonecmd.vacancy_management.modules.candidate.dto.CreateCandidateDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.dto.ListJobResponseDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.dto.ProfileCandidateDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.jhonecmd.vacancy_management.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.jhonecmd.vacancy_management.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.jhonecmd.vacancy_management.modules.candidate.useCases.ProfileCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/candidates")
@Tag(name = "Candidates", description = "Routes intended for candidates.")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

    @PostMapping("")
    @Operation(summary = "Create a candidate.", description = "This route is designed to create a candidate.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Candidate created successfully"),

            @ApiResponse(responseCode = "409", description = "Candidate already exists", content = {
                    @Content(schema = @Schema(implementation = String.class, example = "Candidate already exists."))
            }),

            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class))))
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CreateCandidateDTO createCandidateDTO) {
        try {

            var candidateEntity = CandidateEntity.builder().name(createCandidateDTO.getName())
                    .email(createCandidateDTO.getEmail()).password(createCandidateDTO.getPassword())
                    .description(createCandidateDTO.getDescription()).build();
            this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (ResourceAlreadyExists ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "View user profile.", description = "This route is designed to view profile of candidate.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateDTO.class))
            })
    })
    public ResponseEntity<Object> profile(HttpServletRequest request) {
        try {

            var candidateId = request.getAttribute("candidateId");
            var result = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "List of available job openings for candidates.", description = "This route is designed to list available job openings for candidates using a filter.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ListJobResponseDTO.class)))
            })
    })
    public ResponseEntity<Object> listJobFilter(@RequestParam(required = false) String filter) {
        try {

            var result = this.listAllJobsByFilterUseCase.execute(filter);
            return ResponseEntity.ok(result);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
