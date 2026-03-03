package br.com.jhonecmd.vacancy_management.modules.candidate.dto;

import java.util.UUID;

import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity.Level;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListJobResponseDTO {
    private UUID id;
    private String name;
    private String description;
    private String benefits;
    private Level level;
    private String companyName;
}
