package br.com.jhonecmd.vacancy_management.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {

    public static String objectToJson(Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String generateToken(UUID companyId, String secretKey) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofDays(7));

        var token = JWT.create().withIssuer("java-vagas").withSubject(companyId.toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .withExpiresAt(expiresIn).sign(algorithm);

        return token;
    }
}
