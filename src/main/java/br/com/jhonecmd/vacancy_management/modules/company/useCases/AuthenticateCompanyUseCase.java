package br.com.jhonecmd.vacancy_management.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.jhonecmd.vacancy_management.exceptions.InvalidCredentials;
import br.com.jhonecmd.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.jhonecmd.vacancy_management.modules.company.dto.AuthCompanyResponseDTO;
import br.com.jhonecmd.vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class AuthenticateCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) {
        var company = this.companyRepository.findByEmail(authCompanyDTO.getEmail()).orElseThrow(() -> {
            throw new InvalidCredentials();
        });

        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentials();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofDays(7));

        var token = JWT.create().withIssuer("java-vagas").withSubject(company.getId().toString())
                .withExpiresAt(expiresIn).sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder().access_token(token)
                .expiresAt(expiresIn.toEpochMilli()).build();

        return authCompanyResponseDTO;
    }

}
