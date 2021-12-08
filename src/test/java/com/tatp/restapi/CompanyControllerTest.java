package com.tatp.restapi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CompanyRepository companyRepository;

    @BeforeEach
    void cleanRepository(){
        companyRepository.clearAll();
    }

    @Test
    void should_get_all_companies_when_perform_given_companies_and_company_id() throws Exception {
        //given
        companyRepository.create(new Company(1,"Spring1", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(2,"Spring2", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(3,"Spring3", new EmployeeRepository().findAll()));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("Spring1"));
    }
    @Test
    void should_return_company_when_perform_get_given_companies() throws Exception {
        //given
        companyRepository.create(new Company(1,"Spring1", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(2,"Spring2", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(3,"Spring3", new EmployeeRepository().findAll()));
        //when
        //then
        mockMvc.perform(get("/companies/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Spring2"));
    }

}