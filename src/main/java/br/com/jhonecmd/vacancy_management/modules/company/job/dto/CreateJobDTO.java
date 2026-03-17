package br.com.jhonecmd.vacancy_management.modules.company.job.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobDTO {

    @Schema(example = "Vaga Junior")
    private String name;

    @Schema(example = "Gym pass, auxilio alimentação e refeição, vaga remota.")
    private String benefits;

    @Schema(example = "vaga destinada a desenvolvedora junior.")
    private String description;

    @Schema(example = "JUNIOR")
    private String level;

}
