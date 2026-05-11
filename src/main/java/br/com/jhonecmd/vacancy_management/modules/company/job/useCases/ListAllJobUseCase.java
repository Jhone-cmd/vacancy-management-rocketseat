package br.com.jhonecmd.vacancy_management.modules.company.job.useCases;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.vacancy_management.modules.company.job.dto.ListJobResponseByCompanyDTO;
import br.com.jhonecmd.vacancy_management.modules.company.job.repositories.JobRepository;

@Service
public class ListAllJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<ListJobResponseByCompanyDTO> execute(UUID companyId) {

        var jobs = this.jobRepository.findByCompanyId(companyId);

        return jobs.stream()
                .map((job) -> ListJobResponseByCompanyDTO.builder().id(job.getId()).name(job.getName())
                        .description(job.getDescription())
                        .level(job.getLevel()).benefits(job.getBenefits()).build())
                .toList();

    }
}
