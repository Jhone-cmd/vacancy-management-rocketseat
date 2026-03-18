package br.com.jhonecmd.vacancy_management.modules.company.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

import br.com.jhonecmd.vacancy_management.exceptions.InvalidCredentials;
import br.com.jhonecmd.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.jhonecmd.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.jhonecmd.vacancy_management.modules.company.repositories.CompanyRepository;
import br.com.jhonecmd.vacancy_management.modules.company.useCases.AuthenticateCompanyUseCase;

@ExtendWith(MockitoExtension.class)
public class AuthenticateCompanyUseCaseTest {

        @InjectMocks
        private AuthenticateCompanyUseCase authenticateCompanyUseCase;

        @Mock
        private CompanyRepository companyRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @BeforeEach
        void setup() {

                ReflectionTestUtils.setField(authenticateCompanyUseCase, "secretKey", "secret_test_123");
        }

        @Test
        @DisplayName("Should not be able to authenticate with incorrect email.")
        public void should_not_be_able_to_authenticate_with_incorrect_email() {

                var authDTO = new AuthCompanyDTO("company@email.com", "encoded_password");

                when(companyRepository.findByEmail(authDTO.getEmail()))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> authenticateCompanyUseCase.execute(authDTO))
                                .isInstanceOf(InvalidCredentials.class);
                verify(passwordEncoder, never()).matches(anyString(), anyString());
        }

        @Test
        @DisplayName("Should not be able to authenticate with wrong password.")
        public void should_not_be_able_to_authenticate_with_wrong_password() {

                var authDTO = new AuthCompanyDTO("company@email.com", "wrong_password");
                var company = CompanyEntity.builder()
                                .email("company@email.com")
                                .password("encoded_password")
                                .build();

                when(companyRepository.findByEmail(authDTO.getEmail()))
                                .thenReturn(Optional.of(company));

                when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

                assertThatThrownBy(() -> authenticateCompanyUseCase.execute(authDTO))
                                .isInstanceOf(InvalidCredentials.class);
        }

        @Test
        @DisplayName("Should be able possible to authenticate a company and return the token.")
        public void should_be_able_to_authenticate_a_company() {

                var password = "password123";
                var company = CompanyEntity.builder()
                                .id(UUID.randomUUID())
                                .email("company@email.com.com")
                                .password("encoded_password")
                                .build();

                var authDTO = new AuthCompanyDTO("company@email.com", password);

                when(companyRepository.findByEmail(authDTO.getEmail()))
                                .thenReturn(Optional.of(company));

                when(passwordEncoder.matches(password, company.getPassword()))
                                .thenReturn(true);

                var result = authenticateCompanyUseCase.execute(authDTO);

                assertThat(result.getAccess_token()).isNotNull();
                assertThat(result.getExpiresAt()).isGreaterThan(0);
                verify(companyRepository, times(1)).findByEmail(authDTO.getEmail());
        }
}
