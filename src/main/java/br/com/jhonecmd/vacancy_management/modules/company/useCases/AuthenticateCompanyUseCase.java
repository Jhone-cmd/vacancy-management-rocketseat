package br.com.jhonecmd.vacancy_management.modules.company.useCases;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.vacancy_management.exceptions.InvalidCredentials;
import br.com.jhonecmd.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.jhonecmd.vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class AuthenticateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = this.companyRepository.findByEmail(authCompanyDTO.getEmail()).orElseThrow(() -> {
            throw new InvalidCredentials();
        });

        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentials();
        }

    }

}
