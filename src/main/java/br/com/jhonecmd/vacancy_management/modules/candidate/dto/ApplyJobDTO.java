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
public class ApplyJobDTO {
    @Schema(example = "3fa85f64-5717-4562-b3fc-2c963f66dda8")
    private String jobId;
}
