package br.com.jhonecmd.vacancy_management.modules.candidate.usecases;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.jhonecmd.vacancy_management.exceptions.CandidateNotFound;
import br.com.jhonecmd.vacancy_management.modules.candidate.dto.ProfileCandidateDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.jhonecmd.vacancy_management.modules.candidate.useCases.ProfileCandidateUseCase;

@ExtendWith(MockitoExtension.class)
public class ProfileCandidateUseCaseTest {

    @InjectMocks
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Test
    @DisplayName("Should not be able to view profile of candidate if him not found.")
    public void should_not_be_able_to_view_profile_of_candidate_if_him_not_found() {

        var random = UUID.randomUUID();

        assertThatThrownBy(() -> this.profileCandidateUseCase.execute(random))
                .isInstanceOf(
                        CandidateNotFound.class);
        verify(candidateRepository, times(1)).findById(random);
    }

    @Test
    @DisplayName("Should be able to view profile a candidate.")
    public void should_be_able_to_view_profile_a_candidate() {

        var candidateId = UUID.randomUUID();
        var candidate = CandidateEntity.builder()
                .id(candidateId)
                .name("John Doe")
                .email("john@example.com")
                .build();

        when(this.candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        var result = this.profileCandidateUseCase.execute(candidateId);

        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(ProfileCandidateDTO.class);
        assertThat(result.getName()).isEqualTo("John Doe");
    }
}
