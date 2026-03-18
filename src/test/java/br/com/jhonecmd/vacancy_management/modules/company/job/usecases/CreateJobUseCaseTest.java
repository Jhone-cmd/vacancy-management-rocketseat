package br.com.jhonecmd.vacancy_management.modules.company.job.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.jhonecmd.vacancy_management.exceptions.CompanyNotFound;
import br.com.jhonecmd.vacancy_management.exceptions.ResourceAlreadyExists;
import br.com.jhonecmd.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity.Level;
import br.com.jhonecmd.vacancy_management.modules.company.job.repositories.JobRepository;
import br.com.jhonecmd.vacancy_management.modules.company.job.useCases.CreateJobUseCase;
import br.com.jhonecmd.vacancy_management.modules.company.repositories.CompanyRepository;

@ExtendWith(MockitoExtension.class)
public class CreateJobUseCaseTest {

    @InjectMocks
    private CreateJobUseCase createJobUseCase;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private JobRepository jobRepository;

    @Test
    @DisplayName("Should not be able to create job if name exists.")
    public void should_not_be_able_to_create_job_if_name_exists() {

        var job = JobEntity.builder().name("Java Developer").build();

        when(jobRepository.findByName(job.getName()))
                .thenReturn(Optional.of(new JobEntity()));

        assertThatThrownBy(() -> createJobUseCase.execute(job))
                .isInstanceOf(ResourceAlreadyExists.class);

        verify(jobRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should not be able to create job if company not found.")
    public void should_not_be_able_to_create_job_if_company_not_found() {

        var companyId = UUID.randomUUID();
        var job = JobEntity.builder()
                .name("React Developer")
                .companyId(companyId)
                .build();

        when(jobRepository.findByName(job.getName())).thenReturn(Optional.empty());
        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> createJobUseCase.execute(job))
                .isInstanceOf(CompanyNotFound.class);

        verify(jobRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should be able to create a new job.")
    public void should_be_able_to_create_a_new_job() {

        var companyId = UUID.randomUUID();
        var job = JobEntity.builder()
                .name("Java Developer")
                .description("Vaga para Backend")
                .benefits("VR, VA, Home Office")
                .level(Level.JUNIOR)
                .companyId(companyId)
                .build();

        when(jobRepository.findByName(job.getName())).thenReturn(Optional.empty());
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(new CompanyEntity()));

        createJobUseCase.execute(job);

        verify(jobRepository, times(1)).save(job);
    }
}
