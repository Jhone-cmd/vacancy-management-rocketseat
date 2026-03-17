package br.com.jhonecmd.vacancy_management.modules.company.job.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.vacancy_management.exceptions.CompanyNotFound;
import br.com.jhonecmd.vacancy_management.exceptions.ResourceAlreadyExists;
import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.repositories.JobRepository;
import br.com.jhonecmd.vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class CreateJobUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobRepository jobRepository;

    public void execute(JobEntity jobEntity) {

        this.jobRepository.findByName(jobEntity.getName()).ifPresent((job) -> {
            throw new ResourceAlreadyExists();
        });

        this.companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
            throw new CompanyNotFound();
        });

        this.jobRepository.save(jobEntity);
        return;
    }
}
