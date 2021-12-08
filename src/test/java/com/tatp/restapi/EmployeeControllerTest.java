package com.tatp.restapi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void cleanRepository(){
        employeeRepository.clearAll();
    }

    @Test
    void should_get_all_employees_when_perform_given_employees() throws Exception {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee(1, "Jason",18,"male",99999999);
        employeeRepository.create(employee);
        employees.add(employee);
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Jason"));
    }

    @Test
    void should_return_employee_when_perform_post_given_employee() throws Exception {
        String employee="{\n" +
                "        \"name\": \"adddddddddddddddddJason\",\n" +
                "        \"age\": 18,\n" +
                "        \"gender\": \"male\",\n" +
                "        \"salary\": 5\n" +
                "    }";

        mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("adddddddddddddddddJason"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(5));
    }

    @Test
    void should_return_employee_when_perform_get_given_employee_id() throws Exception  {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee(1, "Jason",18,"male",5);
        employeeRepository.create(employee);
        employees.add(employee);
        //when
        //then
        mockMvc.perform(get("/employees/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jason"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(5));
    }


    @Test
    void should_return_employees_when_perform_get_given_employee_gender() throws Exception  {
        //given
        employeeRepository.create(new Employee(1, "Jason",18,"male",5));
        employeeRepository.create(new Employee(2, "Julia",18,"female",5));

        //when
        //then
        mockMvc.perform(get("/employees")
                .param("gender", "male")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Jason"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(18))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(5));
    }

    @Test
    void should_return_employees_when_perform_get_given_page_and_pageSize() throws Exception {
        //given
        employeeRepository.create(new Employee(1, "Jason",18,"male",5));
        employeeRepository.create(new Employee(2, "Julia1",18,"female",5));
        employeeRepository.create(new Employee(3, "Julia2",18,"female",5));
        employeeRepository.create(new Employee(4, "Julia3",18,"female",5));
        employeeRepository.create(new Employee(5, "Julia4",18,"female",5));
        employeeRepository.create(new Employee(6, "Julia5",18,"female",5));

        //when
        //then
        mockMvc.perform(get("/employees")
                .param("page", String.valueOf(1))
                .param("pageSize", String.valueOf(5))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5)));
    }

//    @PutMapping("/{id}") //NOT PATCH?
//    public Employee editEmployee(@PathVariable Integer id, @RequestBody Employee updatedEmployee){
//        Employee employee = employeeRepository.findById(id);
//        if (updatedEmployee.getAge() != null && !updatedEmployee.getAge().equals(0))
//            employee.setAge(updatedEmployee.getAge());
//        if (updatedEmployee.getSalary() != null && !updatedEmployee.getSalary().equals(0))
//            employee.setSalary(updatedEmployee.getSalary());
//        return employeeRepository.save(id, employee);
//    }
    @Test
    void should_return_employee_when_perform_put_given_updated_employee_and_id() throws Exception {
        //given
        employeeRepository.create(new Employee(1, "Jason",18,"male",5));
        String employee="{\n" +
                "        \"age\": 20,\n" +
                "        \"salary\": 500\n" +
                "    }";
        //when
        //then
        mockMvc.perform(put("/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jason"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(500));
    }

}