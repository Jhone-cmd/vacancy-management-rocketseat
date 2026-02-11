package br.com.jhonecmd.vacancy_management.modules.company.job.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.vacancy_management.modules.company.job.dto.CreateJobDTO;
import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies/jobs")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("")
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
