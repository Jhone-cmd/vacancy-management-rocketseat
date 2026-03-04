package br.com.jhonecmd.vacancy_management.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.vacancy_management.exceptions.ErrorMessageDTO;
import br.com.jhonecmd.vacancy_management.modules.company.dto.CreateCompanyDTO;
import br.com.jhonecmd.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.jhonecmd.vacancy_management.modules.company.useCases.CreateCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/companies")
@Tag(name = "Companies", description = "Routes intended for companies.")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("")
    @Operation(summary = "Create a company.", description = "This route is designed to create a company.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Company created successfully"),

            @ApiResponse(responseCode = "409", description = "Resource already exists.", content = {
                    @Content(schema = @Schema(implementation = String.class, example = "Resource already exists!"))
            }),

            @ApiResponse(responseCode = "400", description = "Validation errors", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorMessageDTO.class))))
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CreateCompanyDTO createCompanyDTO) {
        try {

            var companyEntity = CompanyEntity.builder().name(createCompanyDTO.getName())
                    .email(createCompanyDTO.getEmail()).password(createCompanyDTO.getPassword())
                    .description(createCompanyDTO.getDescription()).webSite(createCompanyDTO.getWebSite()).build();
            this.createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
