package com.tatp.restapi.controller;
import com.tatp.restapi.entity.Employee;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeRepositoryMongo employeeRepositoryMongo;

    @BeforeEach
    void cleanRepository(){
        employeeRepositoryMongo.deleteAll();
    }

    @Test
    void should_get_all_employees_when_perform_given_employees() throws Exception {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee("Jason",18,"male",99999999);
        Employee savedEmployee = employeeRepositoryMongo.save(employee);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(savedEmployee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Jason"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(18));
    }

    @Test
    void should_return_employee_when_perform_get_given_employee_id() throws Exception  {
        //given
        Employee employee = new Employee("Jason",18,"male",5);
        Employee saveEmployee = employeeRepositoryMongo.save(employee);
        //when
        //then
        mockMvc.perform(get("/employees/" + saveEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(saveEmployee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jason"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"));
    }

    @Test
    void should_return_employee_when_perform_post_given_employee() throws Exception {
        String employee="{\n" +
                "    \"name\": \"AddAddJason\",\n" +
                "    \"age\": 10,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 5,\n" +
                "    \"companyID\":1\n" +
                "}";

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("AddAddJason"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(5));
        assertEquals(1, employeeRepositoryMongo.findAll().size());
    }


    @Test
    void should_return_employees_when_perform_get_given_employee_gender() throws Exception  {
        //given
        employeeRepositoryMongo.save(new Employee("Jason",18,"male",5));
        employeeRepositoryMongo.save(new Employee("Jayson",18,"female",5));

        //when
        //then
        mockMvc.perform(get("/employees")
                .param("gender", "male")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Jason"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"));
    }

    @Test
    void should_return_employees_when_perform_get_given_page_and_pageSize() throws Exception {
        //given
        employeeRepositoryMongo.save(new Employee("Jason1",18,"male",5));
        employeeRepositoryMongo.save(new Employee("Jason2",18,"female",5));
        employeeRepositoryMongo.save(new Employee("Jason3",18,"female",5));
        employeeRepositoryMongo.save(new Employee("Jason4",18,"female",5));
        employeeRepositoryMongo.save(new Employee("Jason5",18,"female",5));
        employeeRepositoryMongo.save(new Employee("Jason6",18,"female",5));

        //when
        //then
        mockMvc.perform(get("/employees")
                .param("page", String.valueOf(1))
                .param("pageSize", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Jason1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"));
    }

    @Test
    void should_return_employee_when_perform_put_given_updated_employee_and_id() throws Exception {
        //given
        Employee saveEmployee = employeeRepositoryMongo.save(new Employee( "Jason",18,"male",5));
        String employee="{\n" +
                "        \"age\": 20,\n" +
                "        \"salary\": 500\n" +
                "    }";
        //when
        //then
        mockMvc.perform(put("/employees/" + saveEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jason"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"));
    }

    @Test
    void should_return_nothing_when_perform_delete_given_employee_id() throws Exception {
        //given
        Employee saveEmployee = employeeRepositoryMongo.save(new Employee("Jason",18,"male",5));

        //when
        //then
        System.out.printf("/employees/" + saveEmployee.getId());
        mockMvc.perform(delete("/employees/" + saveEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        assertEquals(0, employeeRepositoryMongo.findAll().size());

    }

}