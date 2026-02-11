package br.com.jhonecmd.vacancy_management.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.jhonecmd.vacancy_management.exceptions.InvalidCredentials;
import br.com.jhonecmd.vacancy_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;

@Service
public class AuthenticateCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateDTO authCandidateDTO) {

        var candidate = this.candidateRepository.findByEmail(authCandidateDTO.getEmail()).orElseThrow(() -> {
            throw new InvalidCredentials();
        });

        var passwordMatches = this.passwordEncoder.matches(authCandidateDTO.getPassword(), candidate.getPassword());

        if (!passwordMatches) {
            throw new InvalidCredentials();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresAt = Instant.now().plus(Duration.ofDays(7));

        var token = JWT.create().withIssuer("java-vagas").withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("candidate"))
                .withExpiresAt(expiresAt).sign(algorithm);

        var authCandidateResponseDTO = AuthCandidateResponseDTO.builder().access_token(token).build();

        return authCandidateResponseDTO;
    }
}
