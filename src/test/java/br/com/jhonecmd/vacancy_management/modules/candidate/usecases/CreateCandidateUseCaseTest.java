package br.com.jhonecmd.vacancy_management.modules.candidate.usecases;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.jhonecmd.vacancy_management.exceptions.ResourceAlreadyExists;
import br.com.jhonecmd.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.jhonecmd.vacancy_management.modules.candidate.useCases.CreateCandidateUseCase;

@ExtendWith(MockitoExtension.class)
public class CreateCandidateUseCaseTest {

    @InjectMocks
    private CreateCandidateUseCase createCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should not be able to create a candidate if email already exists.")
    public void should_not_be_able_to_create_a_candidate_if_email_already_exists() {

        var candidate = new CandidateEntity();
        candidate.setEmail("candidate@email.com");

        when(this.candidateRepository.findByEmail(candidate.getEmail()))
                .thenReturn(Optional.of(new CandidateEntity()));

        assertThatThrownBy(() -> this.createCandidateUseCase.execute(candidate))
                .isInstanceOf(ResourceAlreadyExists.class);
    }

    @Test
    @DisplayName("Should be able to create a candidate.")
    public void should_be_able_to_create_a_candidate() {

        var candidate = new CandidateEntity();
        candidate.setEmail("candidate@email.com");
        candidate.setPassword("password123");

        when(candidateRepository.findByEmail(candidate.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(candidate.getPassword()))
                .thenReturn("password_encrypted");

        this.createCandidateUseCase.execute(candidate);

        assertThat(candidate.getPassword()).isEqualTo("password_encrypted");

        verify(candidateRepository, times(1)).save(candidate);
    }
}
