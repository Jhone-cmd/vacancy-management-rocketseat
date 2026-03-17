package br.com.jhonecmd.vacancy_management.modules.candidate.usecases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import br.com.jhonecmd.vacancy_management.exceptions.CandidateNotFound;
import br.com.jhonecmd.vacancy_management.exceptions.JobNotFound;
import br.com.jhonecmd.vacancy_management.modules.candidate.entities.ApplyJobEntity;
import br.com.jhonecmd.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.ApplyJobRepository;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.jhonecmd.vacancy_management.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found.")
    public void should_not_be_able_to_apply_job_with_candidate_not_found() {
        try {
            this.applyJobCandidateUseCase.execute(null, null);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(CandidateNotFound.class);
        }
    }

    @Test
    @DisplayName("Should not be able to apply job with job not found.")
    public void should_not_be_able_to_apply_job_with_job_not_found() {
        try {

            var candidateId = UUID.randomUUID();
            var candidate = new CandidateEntity();
            candidate.setId(candidateId);

            when(this.candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

            this.applyJobCandidateUseCase.execute(candidateId, null);

        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(JobNotFound.class);
        }
    }

    @Test
    @DisplayName("Should be able to create a new apply job.")
    public void should_be_able_to_create_a_new_apply_job() {

        var candidateId = UUID.randomUUID();
        var jobId = UUID.randomUUID();

        var applyJob = ApplyJobEntity.builder().candidateId(candidateId).jobId(jobId).build();
        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID()).build();

        when(this.candidateRepository.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));

        when(this.jobRepository.findById(jobId)).thenReturn(Optional.of(new JobEntity()));

        when(this.applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = this.applyJobCandidateUseCase.execute(candidateId, jobId);
        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}
