package br.com.jhonecmd.vacancy_management.modules.candidate.usecases;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.jhonecmd.vacancy_management.exceptions.InvalidCredentials;
import br.com.jhonecmd.vacancy_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.jhonecmd.vacancy_management.modules.candidate.useCases.AuthenticateCandidateUseCase;

@ExtendWith(MockitoExtension.class)
public class AuthenticateCandidateUseCaseTest {

        @InjectMocks
        private AuthenticateCandidateUseCase authenticateCandidateUseCase;

        @Mock
        private CandidateRepository candidateRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @BeforeEach
        void setup() {

                ReflectionTestUtils.setField(authenticateCandidateUseCase, "secretKey", "secret_test_123");
        }

        @Test
        @DisplayName("Should not be able to authenticate with incorrect email.")
        public void should_not_be_able_to_authenticate_with_incorrect_email() {

                var authDTO = new AuthCandidateDTO("test@email.com", "encoded_password");

                when(candidateRepository.findByEmail(authDTO.getEmail()))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> authenticateCandidateUseCase.execute(authDTO))
                                .isInstanceOf(InvalidCredentials.class);
                verify(passwordEncoder, never()).matches(anyString(), anyString());
        }

        @Test
        @DisplayName("Should not be able to authenticate with wrong password.")
        public void should_not_be_able_to_authenticate_with_wrong_password() {

                var authDTO = new AuthCandidateDTO("candidate@email.com", "wrong_password");
                var candidate = CandidateEntity.builder()
                                .email("candidate@email.com")
                                .password("encoded_password")
                                .build();

                when(candidateRepository.findByEmail(authDTO.getEmail()))
                                .thenReturn(Optional.of(candidate));

                when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

                assertThatThrownBy(() -> authenticateCandidateUseCase.execute(authDTO))
                                .isInstanceOf(InvalidCredentials.class);
        }

        @Test
        @DisplayName("Should be able possible to authenticate a candidate and return the token.")
        public void should_be_able_to_authenticate_a_candidate() {

                var password = "password123";
                var candidate = CandidateEntity.builder()
                                .id(UUID.randomUUID())
                                .email("candidate@email.com")
                                .password("encoded_password")
                                .build();

                var authDTO = new AuthCandidateDTO("candidate@email.com", password);

                when(candidateRepository.findByEmail(authDTO.getEmail()))
                                .thenReturn(Optional.of(candidate));

                when(passwordEncoder.matches(password, candidate.getPassword()))
                                .thenReturn(true);

                var result = authenticateCandidateUseCase.execute(authDTO);

                assertThat(result.getAccess_token()).isNotNull();
                assertThat(result.getExpiresAt()).isGreaterThan(0);
                verify(candidateRepository, times(1)).findByEmail(authDTO.getEmail());
        }

}
