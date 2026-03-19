package br.com.jhonecmd.vacancy_management.modules.company.controllers;

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

import br.com.jhonecmd.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.jhonecmd.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.jhonecmd.vacancy_management.modules.company.repositories.CompanyRepository;
import br.com.jhonecmd.vacancy_management.utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthCompanyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        companyRepository.deleteAll();

        var company = CompanyEntity.builder().name("Company-test").email("company@email.com")
                .password(
                        passwordEncoder.encode("1234567890"))
                .description("description test")
                .webSite("https://www.company.org").build();

        company = this.companyRepository.saveAndFlush(company);
    }

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("Should be able to authenticate a company.")
    public void should_be_able_to_authenticate_a_company() throws Exception {

        var authCompanyDTO = AuthCompanyDTO.builder().email("company@email.com").password("1234567890").build();

        mvc.perform(MockMvcRequestBuilders.post("/companies/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(authCompanyDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should not be able to authenticate a company if incorrect email.")
    public void should_not_be_able_to_authenticate_a_company_if_incorrect_email() throws Exception {

        var authCompanyDTO = AuthCompanyDTO.builder().email("companyjava@email.com").password("1234567890").build();

        mvc.perform(MockMvcRequestBuilders.post("/companies/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(authCompanyDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Should not be able to authenticate a company if wrong password.")
    public void should_not_be_able_to_authenticate_a_company_if_wrong_password() throws Exception {

        var authCompanyDTO = AuthCompanyDTO.builder().email("company@email.com").password("1234567891").build();

        mvc.perform(MockMvcRequestBuilders.post("/companies/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(authCompanyDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
