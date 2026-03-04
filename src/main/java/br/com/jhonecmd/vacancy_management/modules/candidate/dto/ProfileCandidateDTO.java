package br.com.jhonecmd.vacancy_management.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateDTO {

    @Schema(example = "85019100-d24b-4f27-897b-56ff22dc3b79")
    private String id;

    @Schema(example = "John Doe")
    private String name;

    @Schema(example = "johndoe@email.com")
    private String email;

    @Schema(example = "Buscando a minha primeira vaga dev.")
    private String description;

}
