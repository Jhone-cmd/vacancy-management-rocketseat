package br.com.jhonecmd.vacancy_management.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCandidateDTO {
    private String id;
    private String name;
    private String email;
    private String description;

}
