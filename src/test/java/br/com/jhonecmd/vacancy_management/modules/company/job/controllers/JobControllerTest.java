package br.com.jhonecmd.vacancy_management.modules.company.job.controllers;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.jhonecmd.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.dto.CreateJobDTO;
import br.com.jhonecmd.vacancy_management.modules.company.repositories.CompanyRepository;
import br.com.jhonecmd.vacancy_management.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
// @AutoConfigureMockMvc
public class JobControllerTest {

        @Value("${security.token.secret}")
        private String secret;

        private MockMvc mvc;

        @Autowired
        private CompanyRepository companyRepository;

        @Autowired
        private WebApplicationContext context;

        @BeforeEach
        public void setup() {
                mvc = MockMvcBuilders.webAppContextSetup(context)
                                .apply(SecurityMockMvcConfigurers.springSecurity())
                                .build();
        }

        @Test
        @DisplayName("Should be able to create a new job.")
        public void should_be_able_to_create_a_new_job() throws Exception {

                var company = CompanyEntity.builder().name("Company-test").email("company@email.com")
                                .password("1234567890").description("description test")
                                .webSite("https://www.company.org").build();

                company = this.companyRepository.saveAndFlush(company);

                var createdJobDTO = CreateJobDTO.builder().name("java test-" + UUID.randomUUID())
                                .benefits("benefits test")
                                .description("description test").level("junior").build();

                var result = mvc.perform(MockMvcRequestBuilders.post("/companies/jobs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createdJobDTO))
                                .header("Authorization",
                                                TestUtils.generateToken(
                                                                company.getId(),
                                                                secret)))
                                .andExpect(MockMvcResultMatchers.status().isCreated());

                System.out.println(result);
        }

}
