package br.com.jhonecmd.vacancy_management.modules.company.job.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.vacancy_management.exceptions.ErrorMessageDTO;
import br.com.jhonecmd.vacancy_management.modules.company.job.dto.CreateJobDTO;
import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies/jobs")
@Tag(name = "Jobs", description = "Routes intended for jobs.")
@SecurityRequirement(name = "auth")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("")
    @PreAuthorize("hasRole('COMPANY')")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Job created successfully"),

            @ApiResponse(responseCode = "409", description = "Resource already exists.", content = {
                    @Content(schema = @Schema(implementation = String.class, example = "Resource already exists!"))
            }),

            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class))))
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
        try {
            var companyId = request.getAttribute("companyId");
            // jobEntity.setCompanyId(UUID.fromString(companyId.toString()));

            var jobEntity = JobEntity.builder().benefits(createJobDTO.getBenefits())
                    .description(createJobDTO.getDescription()).name(createJobDTO.getName())
                    .companyId(UUID.fromString(companyId.toString()))
                    .level(JobEntity.fromValue(createJobDTO.getLevel())).build();

            this.createJobUseCase.execute(jobEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
