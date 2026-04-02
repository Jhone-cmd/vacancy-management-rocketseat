package br.com.jhonecmd.vacancy_management.modules.candidate.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.jhonecmd.vacancy_management.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.jhonecmd.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ListAllJobsByFilterUseCaseTest {

        @InjectMocks
        private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

        @Mock
        private JobRepository jobRepository;

        @Test
        @DisplayName("Should be able to list all jobs by filter.")
        public void should_be_able_to_list_all_jobs_by_filter() {

                var company = CompanyEntity.builder().name("Tech Corp").build();
                var job = JobEntity.builder()
                                .id(UUID.randomUUID())
                                .name("Java Developer")
                                .description("Backend position")
                                .companyEntity(company)
                                .build();

                var job2 = JobEntity.builder()
                                .id(UUID.randomUUID())
                                .name("Java Developer 2")
                                .description("Backend position")
                                .companyEntity(company)
                                .build();

                when(jobRepository.findAll()).thenReturn(List.of(job, job2));

                var result = listAllJobsByFilterUseCase.execute(null);

                assertThat(result).hasSize(2);
                assertThat(result.get(0).getCompanyName()).isEqualTo("Tech Corp");
                verify(jobRepository, times(1)).findAll();
                verify(jobRepository, never()).findByDescriptionContainingIgnoreCase(any());
        }

        @Test
        @DisplayName("Should filter jobs by description when the filter is submitted.")
        public void should_filter_jobs_by_description() {

                var filter = "Java";
                var company = CompanyEntity.builder().name("Dev Company").build();
                var job = JobEntity.builder()
                                .description("Vaga para Java")
                                .companyEntity(company)
                                .build();

                var job2 = JobEntity.builder()
                                .description("Vaga para Java 2")
                                .companyEntity(company)
                                .build();

                when(jobRepository.findByDescriptionContainingIgnoreCase(filter))
                                .thenReturn(List.of(job, job2));

                var result = listAllJobsByFilterUseCase.execute(filter);

                assertThat(result).hasSize(2);
                assertThat(result.get(0).getDescription()).contains(filter);
                verify(jobRepository, times(1)).findByDescriptionContainingIgnoreCase(filter);
                verify(jobRepository, never()).findAll();

        }
}
