package br.com.jhonecmd.vacancy_management.modules.company.job.dto;

import java.util.UUID;

import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity.Level;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListJobResponseByCompanyDTO {

    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(example = "Vaga Junior")
    private String name;

    @Schema(example = "Vaga destinada a desenvolvedora junior.")
    private String description;

    @Schema(example = "Gympass, auxilio alimentação e refeição, vaga remota.")
    private String benefits;

    @Schema(example = "JUNIOR")
    private Level level;

}
