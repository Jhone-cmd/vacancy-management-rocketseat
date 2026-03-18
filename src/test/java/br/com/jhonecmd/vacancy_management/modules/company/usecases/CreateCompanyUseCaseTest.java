package br.com.jhonecmd.vacancy_management.modules.company.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
import br.com.jhonecmd.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.jhonecmd.vacancy_management.modules.company.repositories.CompanyRepository;
import br.com.jhonecmd.vacancy_management.modules.company.useCases.CreateCompanyUseCase;

@ExtendWith(MockitoExtension.class)
public class CreateCompanyUseCaseTest {

    @InjectMocks
    private CreateCompanyUseCase createCompanyUseCase;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should not be able to create a company if email already exists.")
    public void should_not_be_able_to_create_a_company_if_email_already_exists() {

        var company = new CompanyEntity();
        company.setEmail("company@email.com");

        when(companyRepository.findByEmail(company.getEmail()))
                .thenReturn(Optional.of(new CompanyEntity()));

        assertThatThrownBy(() -> createCompanyUseCase.execute(company))
                .isInstanceOf(ResourceAlreadyExists.class);
    }

    @Test
    @DisplayName("Should be able to create a company.")
    public void should_be_able_to_create_a_company() {

        var company = new CompanyEntity();
        company.setEmail("company@email.com");
        company.setPassword("password123");

        when(companyRepository.findByEmail(company.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(company.getPassword()))
                .thenReturn("password_encrypted");

        this.createCompanyUseCase.execute(company);

        assertThat(company.getPassword()).isEqualTo("password_encrypted");

        verify(companyRepository, times(1)).save(company);
    }
}
