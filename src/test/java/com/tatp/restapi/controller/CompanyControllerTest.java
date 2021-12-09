package com.tatp.restapi.controller;
import com.tatp.restapi.entity.Company;
import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.repository.CompanyRepository;
import com.tatp.restapi.repository.CompanyRepositoryMongo;
import com.tatp.restapi.repository.EmployeeRepository;
import com.tatp.restapi.repository.EmployeeRepositoryMongo;
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
    CompanyRepositoryMongo companyRepositoryMongo;
    @Autowired
    EmployeeRepositoryMongo employeeRepositoryMongo;

    @BeforeEach
    void cleanRepository(){
        companyRepositoryMongo.deleteAll();
    }

    @Test
    void should_get_all_companies_when_perform_given_companies_and_company_id() throws Exception {
        //given
        companyRepositoryMongo.save(new Company("Spring1"));
        companyRepositoryMongo.save(new Company("Spring2"));
        companyRepositoryMongo.save(new Company("Spring3"));
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
        companyRepositoryMongo.save(new Company("Spring1"));
        companyRepositoryMongo.save(new Company("Spring2"));
        companyRepositoryMongo.save(new Company("Spring3"));
        //when
        //then
        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(company))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("addSpringBoot"));
        assertEquals(4 ,companyRepositoryMongo.findAll().size());
    }

    @Test
    void should_return_employees_when_perform_get_given_companies_and_company_id() throws Exception {
        //given
        Company saveCompany = companyRepositoryMongo.save(new Company("Spring1"));
        companyRepositoryMongo.save(new Company("Spring2"));
        companyRepositoryMongo.save(new Company("Spring3"));
        Employee employee1 = new Employee("Jason2",18,"male",5, saveCompany.getId());
        Employee employee2 = new Employee("Jason1",18,"male",5, saveCompany.getId());
        employeeRepositoryMongo.save(employee1);
        employeeRepositoryMongo.save(employee2);

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/companies/"+ saveCompany.getId() +"/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }
    @Test
    void should_return_companies_when_perform_get_given_page_and_pageSize() throws Exception {
        //given
        companyRepositoryMongo.save(new Company("Spring1"));
        companyRepositoryMongo.save(new Company("Spring2"));
        companyRepositoryMongo.save(new Company("Spring3"));
        companyRepositoryMongo.save(new Company("Spring4"));
        companyRepositoryMongo.save(new Company("Spring5"));
        companyRepositoryMongo.save(new Company("Spring6"));

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
        Company saveCompany = companyRepositoryMongo.save(new Company("Spring1"));
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
        companyRepository.create(new Company("1","Spring1", new EmployeeRepository().findAll()));

        //when
        //then
        mockMvc.perform(delete("/companies/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        assertEquals(0, companyRepository.findAll().size());
    }



}