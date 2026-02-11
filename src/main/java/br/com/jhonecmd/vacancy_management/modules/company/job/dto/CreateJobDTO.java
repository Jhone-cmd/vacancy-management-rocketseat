package br.com.jhonecmd.vacancy_management.modules.company.job.dto;

import lombok.Data;

@Data
public class CreateJobDTO {

    private String name;
    private String benefits;
    private String description;
    private String level;

}
