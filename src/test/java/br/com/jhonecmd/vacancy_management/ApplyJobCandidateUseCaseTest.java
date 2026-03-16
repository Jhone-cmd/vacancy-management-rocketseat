package br.com.jhonecmd.vacancy_management;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhonecmd.vacancy_management.exceptions.CandidateNotFound;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.jhonecmd.vacancy_management.modules.candidate.useCases.ApplyJobCandidateUseCase;
import br.com.jhonecmd.vacancy_management.modules.company.job.repositories.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found.")
    public void should_not_be_able_to_apply_job_with_candidate_not_found() {
        try {
            this.applyJobCandidateUseCase.execute(null, null);
        } catch (Exception ex) {
            assertThat(ex).isInstanceOf(CandidateNotFound.class);
        }
    }
}
