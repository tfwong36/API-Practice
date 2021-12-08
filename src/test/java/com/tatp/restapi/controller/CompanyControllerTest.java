package com.tatp.restapi.controller;
import com.tatp.restapi.entity.Company;
import com.tatp.restapi.repository.CompanyRepository;
import com.tatp.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    @Autowired
    EmployeeRepository employeeRepository;

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
        String company = "{\n" +
                "    \"companyName\": \"addSpringBoot\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Jason\",\n" +
                "            \"age\": 18,\n" +
                "            \"gender\": \"male\",\n" +
                "            \"salary\": 5\n" +
                "        }]}";
        //given
        companyRepository.create(new Company(1,"Spring1", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(2,"Spring2", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(3,"Spring3", new EmployeeRepository().findAll()));
        //when
        //then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(company))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("addSpringBoot"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(4));
    }

    @Test
    void should_return_employees_when_perform_get_given_companies_and_company_id() throws Exception {
        //given
        companyRepository.create(new Company(1,"Spring1", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(2,"Spring2", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(3,"Spring3", new EmployeeRepository().findAll()));

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/1/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(companyRepository.findAll().size())));
    }
    @Test
    void should_return_companies_when_perform_get_given_page_and_pageSize() throws Exception {
        //given
        companyRepository.create(new Company(1,"Spring1", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(2,"Spring2", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(3,"Spring3", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(4,"Spring4", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(5,"Spring5", new EmployeeRepository().findAll()));
        companyRepository.create(new Company(6,"Spring6", new EmployeeRepository().findAll()));

        //when
        //then
        mockMvc.perform(get("/companies")
                .param("page", String.valueOf(1))
                .param("pageSize", String.valueOf(5))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5)));
    }
    @Test
    void should_return_employee_when_perform_put_given_updated_employee_and_id() throws Exception {
        //given
        companyRepository.create(new Company(1,"Spring1", new EmployeeRepository().findAll()));
        String company="{\"companyName\": \"Spring111\"}";
        //when
        //then
        mockMvc.perform(put("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(company))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Spring111"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    void should_return_nothing_when_perform_delete_given_employee_id() throws Exception {
        //given
        companyRepository.create(new Company(1,"Spring1", new EmployeeRepository().findAll()));

        //when
        //then
        mockMvc.perform(delete("/companies/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        assertEquals(0, companyRepository.findAll().size());

    }
}