package br.com.jhonecmd.vacancy_management.modules.candidate.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.vacancy_management.modules.candidate.dto.ListJobResponseDTO;
import br.com.jhonecmd.vacancy_management.modules.company.job.repositories.JobRepository;

@Service
public class ListAllJobsByFilterUseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<ListJobResponseDTO> execute(String filter) {

        var jobs = (filter == null)
                ? this.jobRepository.findAll()
                : this.jobRepository.findByDescriptionContainingIgnoreCase(filter);

        return jobs.stream()
                .map((job) -> ListJobResponseDTO.builder().id(job.getId()).name(job.getName())
                        .description(job.getDescription()).benefits(job.getBenefits())
                        .level(job.getLevel())
                        .companyName(job.getCompanyEntity().getName()).build())
                .toList();

    }
}
