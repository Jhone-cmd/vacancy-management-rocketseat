package br.com.jhonecmd.vacancy_management.modules.candidate.controllers;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.jhonecmd.vacancy_management.modules.candidate.dto.CreateCandidateDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.jhonecmd.vacancy_management.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CandidateControllerTest {

        @Value("${security.token.secret.candidate}")
        private String secret;

        @Autowired
        private MockMvc mvc;

        @Autowired
        private CandidateRepository candidateRepository;

        @BeforeEach
        void setup() {

                candidateRepository.deleteAll();
                var candidate = CandidateEntity.builder().name("Candidate Test").email("candidate@email.com")
                                .password("1234567890").build();
                candidate = candidateRepository.saveAndFlush(candidate);
        }

        @Test
        @DisplayName("Should be able to create a new candidate.")
        public void should_be_able_to_create_a_new_candidate() throws Exception {

                var createdCandidateDTO = CreateCandidateDTO.builder().name("Candidate Test")
                                .email("candidatetest@email.com")
                                .password("1234567890").build();

                mvc.perform(MockMvcRequestBuilders.post("/candidates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createdCandidateDTO)))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        @DisplayName("Should not be able to create a new candidate if him already exists.")
        public void should_not_be_able_to_create_a_new_candidate_if_him_already_exists() throws Exception {

                var createdCandidateDTO = CreateCandidateDTO.builder().name("Candidate Test")
                                .email("candidate@email.com")
                                .password("1234567890").build();

                mvc.perform(MockMvcRequestBuilders.post("/candidates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createdCandidateDTO)))
                                .andExpect(MockMvcResultMatchers.status().isConflict());
        }

        @Test
        @DisplayName("Should not be able to create a new candidate if validations errors.")
        public void should_not_be_able_to_create_a_new_candidate_if_validations_errors() throws Exception {

                var createdCandidateDTO = CreateCandidateDTO.builder().name("").email("")
                                .password("1234567890").build();

                mvc.perform(MockMvcRequestBuilders.post("/candidates")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createdCandidateDTO)))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        @DisplayName("Should be able to view a profile candidate.")
        public void should_be_able_to_view_a_profile_candidate() throws Exception {

                var candidate = CandidateEntity.builder().name("Candidate Test").email("candidateprofile@email.com")
                                .password("1234567890").build();
                candidate = candidateRepository.saveAndFlush(candidate);

                mvc.perform(MockMvcRequestBuilders.get("/candidates/profile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization",
                                                TestUtils.generateTokenCandidate(
                                                                candidate.getId(),
                                                                secret)))
                                .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("Should not be able to view a profile candidate.")
        public void should_not_be_able_to_view_a_profile_candidate() throws Exception {

                mvc.perform(MockMvcRequestBuilders.get("/candidates/profile")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization",
                                                TestUtils.generateTokenCandidate(
                                                                UUID.randomUUID(),
                                                                secret)))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
}
