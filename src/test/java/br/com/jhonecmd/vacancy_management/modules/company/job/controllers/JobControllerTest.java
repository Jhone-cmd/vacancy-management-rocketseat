package br.com.jhonecmd.vacancy_management.modules.company.job.controllers;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhonecmd.vacancy_management.modules.company.job.dto.CreateJobDTO;

public class JobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    private void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("Should be able to create a new job.")
    public void should_be_able_to_create_a_new_job() throws Exception {

        var createJobDTO = CreateJobDTO.builder().name("java test").benefits("benefits test")
                .description("description test").level("junior").build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/companies/job")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(createJobDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        System.out.println(result);
    }

    private static String objectToJson(Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
