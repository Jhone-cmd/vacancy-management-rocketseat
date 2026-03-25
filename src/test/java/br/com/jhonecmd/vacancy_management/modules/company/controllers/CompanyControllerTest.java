package br.com.jhonecmd.vacancy_management.modules.company.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.jhonecmd.vacancy_management.modules.company.dto.CreateCompanyDTO;
import br.com.jhonecmd.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.jhonecmd.vacancy_management.modules.company.job.repositories.JobRepository;
import br.com.jhonecmd.vacancy_management.modules.company.repositories.CompanyRepository;
import br.com.jhonecmd.vacancy_management.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CompanyControllerTest {

        private MockMvc mvc;

        @Autowired
        private WebApplicationContext context;

        @Autowired
        private CompanyRepository companyRepository;

        @Autowired
        private JobRepository jobRepository;

        @BeforeEach
        public void setup() {
                jobRepository.deleteAll();
                companyRepository.deleteAll();
                mvc = MockMvcBuilders.webAppContextSetup(context).build();
        }

        @Test
        @DisplayName("Should be able to create a new company.")
        public void should_be_able_to_create_a_new_company() throws Exception {

                var createdCompanyDTO = CreateCompanyDTO.builder().name("Company Test").description("Description Test")
                                .email("company@email.com").password("encrypted_password")
                                .webSite("https://wwww.company.com.br")
                                .build();

                mvc.perform(MockMvcRequestBuilders.post("/companies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createdCompanyDTO)))
                                .andExpect(MockMvcResultMatchers.status().isCreated());
        }

        @Test
        @DisplayName("Should not be able to create a new company if him already exists.")
        public void should_not_be_able_to_create_a_new_company_if_him_already_exists() throws Exception {

                companyRepository.deleteAll();
                var company = CompanyEntity.builder().name("Company-test").email("company@email.com")
                                .password("1234567890").description("description test")
                                .webSite("https://www.company.org").build();

                this.companyRepository.saveAndFlush(company);

                var createdCompanyDTO = CreateCompanyDTO.builder().name("Company Test").description("Description Test")
                                .email("company@email.com").password("encrypted_password")
                                .webSite("https://wwww.company.com.br")
                                .build();

                mvc.perform(MockMvcRequestBuilders.post("/companies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createdCompanyDTO)))
                                .andExpect(MockMvcResultMatchers.status().isConflict());
        }

        @Test
        @DisplayName("Should not be able to create a new company if validations errors.")
        public void should_not_be_able_to_create_a_new_company_if_validations_errors() throws Exception {

                var createdCompanyDTO = CreateCompanyDTO.builder().name("").description("Description Test")
                                .email("").password("encrypted_password").webSite("https://wwww.company.com.br")
                                .build();

                mvc.perform(MockMvcRequestBuilders.post("/companies")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(TestUtils.objectToJson(createdCompanyDTO)))
                                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }
}
