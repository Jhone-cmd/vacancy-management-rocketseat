package br.com.jhonecmd.vacancy_management.modules.candidate.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.jhonecmd.vacancy_management.modules.candidate.dto.AuthCandidateDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.jhonecmd.vacancy_management.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthCandidateControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CandidateRepository candidateRepository;

    @BeforeEach
    void setup() {
        candidateRepository.deleteAll();

        var candidate = CandidateEntity.builder().name("Candidate-test").email("candidate@email.com")
                .password(
                        passwordEncoder.encode("1234567890"))
                .description("description test")
                .build();

        candidate = this.candidateRepository.saveAndFlush(candidate);
    }

    @Test
    @DisplayName("Should be able to authenticate a candidate.")
    public void should_be_able_to_authenticate_a_candidate() throws Exception {

        var authCandidateDTO = AuthCandidateDTO.builder().email("candidate@email.com").password("1234567890").build();

        mvc.perform(MockMvcRequestBuilders.post("/candidates/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(authCandidateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should not be able to authenticate a candidate if incorrect email.")
    public void should_not_be_able_to_authenticate_a_candidate_if_incorrect_email() throws Exception {

        var authCandidateDTO = AuthCandidateDTO.builder().email("candidatejava@email.com").password("1234567890")
                .build();

        mvc.perform(MockMvcRequestBuilders.post("/candidates/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(authCandidateDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Should not be able to authenticate a candidate if wrong password.")
    public void should_not_be_able_to_authenticate_a_candidate_if_wrong_password() throws Exception {

        var authCandidateDTO = AuthCandidateDTO.builder().email("candidate@email.com").password("1234567891").build();

        mvc.perform(MockMvcRequestBuilders.post("/candidates/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(authCandidateDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
