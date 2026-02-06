package br.com.jhonecmd.vacancy_management.modules.company.job.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.vacancy_management.exceptions.ResourceAlreadyExists;
import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.repositories.JobRepository;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public void execute(JobEntity jobEntity) {

        this.jobRepository.findByName(jobEntity.getName()).ifPresent((job) -> {
            throw new ResourceAlreadyExists();
        });

        this.jobRepository.save(jobEntity);
        return;
    }
}
