package br.com.jhonecmd.vacancy_management.modules.candidate.controllers;

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

import br.com.jhonecmd.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.jhonecmd.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity.Level;
import br.com.jhonecmd.vacancy_management.modules.company.job.repositories.JobRepository;
import br.com.jhonecmd.vacancy_management.modules.company.repositories.CompanyRepository;
import br.com.jhonecmd.vacancy_management.utils.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CandidateJobControllerTest {

    @Value("${security.token.secret.candidate}")
    private String secret;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobRepository jobRepository;

    @BeforeEach
    void setup() {

        jobRepository.deleteAll();
        companyRepository.deleteAll();
        candidateRepository.deleteAll();

        var company = CompanyEntity.builder()
                .name("Dev Company")
                .email("companydev@email.com")
                .build();
        company = companyRepository.saveAndFlush(company);

        var job = JobEntity.builder()
                .name("Dev Java")
                .description("Vaga para Java")
                .companyId(company.getId())
                .level(Level.JUNIOR)
                .build();

        jobRepository.saveAndFlush(job);

    }

    @Test
    @DisplayName("Should be able to view list jobs.")
    public void should_be_able_to_view_list_jobs() throws Exception {

        var candidate = CandidateEntity.builder().name("Candidate Test").email("candidate@email.com")
                .password("1234567890").build();
        candidate = candidateRepository.saveAndFlush(candidate);

        mvc.perform(MockMvcRequestBuilders.get("/candidates/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .param("filter", "")
                .header("Authorization",
                        TestUtils.generateTokenCandidate(
                                candidate.getId(),
                                secret)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should be able to view list jobs with filter apply.")
    public void should_be_able_to_view_list_jobs_with_filter_apply() throws Exception {

        var candidate = CandidateEntity.builder().name("Candidate Test").email("candidate@email.com")
                .password("1234567890").build();
        candidate = candidateRepository.saveAndFlush(candidate);

        mvc.perform(MockMvcRequestBuilders.get("/candidates/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .param("filter", "java")
                .header("Authorization",
                        TestUtils.generateTokenCandidate(
                                candidate.getId(),
                                secret)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
