package com.tatp.restapi.controller;
import com.tatp.restapi.entity.Company;
import com.tatp.restapi.entity.Employee;
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
        companyRepository.deleteAll();
    }

    @Test
    void should_get_all_companies_when_perform_given_companies_and_company_id() throws Exception {
        //given
        companyRepository.save(new Company("Spring1"));
        companyRepository.save(new Company("Spring2"));
        companyRepository.save(new Company("Spring3"));
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Spring1"));
    }
    @Test
    void should_return_company_when_perform_get_given_companies() throws Exception {
        String company = "{\"name\":\"addSpringBoot\"}";
        //given
        companyRepository.save(new Company("Spring1"));
        companyRepository.save(new Company("Spring2"));
        companyRepository.save(new Company("Spring3"));
        //when
        //then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(company))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("addSpringBoot"));
        assertEquals(4 , companyRepository.findAll().size());
    }

    @Test
    void should_return_employees_when_perform_get_given_companies_and_company_id() throws Exception {
        //given
        Company saveCompany = companyRepository.save(new Company("Spring1"));
        companyRepository.save(new Company("Spring2"));
        companyRepository.save(new Company("Spring3"));
        Employee employee1 = new Employee("Jason2",18,"male",5, saveCompany.getId());
        Employee employee2 = new Employee("Jason1",18,"male",5, saveCompany.getId());
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/"+ saveCompany.getId() +"/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }
    @Test
    void should_return_companies_when_perform_get_given_page_and_pageSize() throws Exception {
        //given
        companyRepository.save(new Company("Spring1"));
        companyRepository.save(new Company("Spring2"));
        companyRepository.save(new Company("Spring3"));
        companyRepository.save(new Company("Spring4"));
        companyRepository.save(new Company("Spring5"));
        companyRepository.save(new Company("Spring6"));

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
        Company saveCompany = companyRepository.save(new Company("Spring1"));
        String company="{\"name\": \"UpdatedSpring\"}";
        //when
        //then
        mockMvc.perform(put("/companies/" + saveCompany.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(company))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("UpdatedSpring"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(saveCompany.getId()));
    }

    @Test
    void should_return_nothing_when_perform_delete_given_employee_id() throws Exception {
        //given
        Company saveCompany = companyRepository.save(new Company("Spring1"));

        //when
        //then
        mockMvc.perform(delete("/companies/" + saveCompany.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        assertEquals(0, companyRepository.findAll().size());
    }
}