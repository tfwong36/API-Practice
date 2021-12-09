package com.tatp.restapi.service;

import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.repository.EmployeeRepository;
import com.tatp.restapi.repository.EmployeeRepositoryMongo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    EmployeeRepositoryMongo employeeRepositoryMongo;
    @InjectMocks
    EmployeeService employeeService;

    @BeforeEach
    void cleanRepository(){
        employeeRepositoryMongo.deleteAll();
    }

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Jason", 10, "male", 1000));
        given(employeeRepositoryMongo.findAll())
                .willReturn(employees);
        //when
        List<Employee> actual = employeeService.findAll();

        //then
        assertEquals(1, actual.size());
        assertEquals("Jason", actual.get(0).getName());
    }

    @Test
    void should_return_a_employee_when_get_employee_given_employee_id() {
        //given
        Employee employee = new Employee("Jason", 10, "male", 1000);
        given(employeeRepositoryMongo.findById(any()))
                .willReturn(java.util.Optional.of(employee));
        //when
        Employee actual = employeeService.findById(any());
        //then
        assertEquals(employee, actual);
    }

    @Test
    void should_return_updated_employee_when_edit_employee_given_updated_employee() {
        //given
        Employee employee = new Employee("Jason", 10, "male", 1000);
        Employee updatedEmployee = new Employee("Jason", 10, "male", 2000);
        given(employeeRepositoryMongo.findById(any()))
                .willReturn(java.util.Optional.of(employee));
        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());
        given(employeeRepositoryMongo.save(any(Employee.class)))
                .willReturn(employee);

        //when
        Employee actual = employeeService.edit(any(), updatedEmployee);

        //then
        assertEquals(employee, actual);
    }

    @Test
    void should_return_employee_when_get_given_employee_id() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee("Jason", 10, "male", 1000);
        employees.add(employee);
        given(employeeRepositoryMongo.findById(employee.getId()))
                .willReturn(java.util.Optional.of(employee));
        //when
        Employee actual = employeeService.findById(any());
        verify(employeeRepositoryMongo).findById(employee.getId());

        //return
        assertEquals(employee, actual);
    }

    @Test
    void should_return_employees_when_get_given_employees_and_gender() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Jason",10,"male", 2000));
        employees.add(new Employee("Santa",20,"female",1000));
        employees.add(new Employee("Klaus",30,"male", 10));
        given(employeeRepositoryMongo.findByGender("male"))
                .willReturn(employees);
        //when
        List<Employee> actual = employeeService.findByGender("male");
        verify(employeeRepositoryMongo).findByGender("male");
        //return
        assertEquals(employees,actual);
    }
    @Test
    void should_return_created_employee_when_create_given_employee() {
        //given
        Employee employee1 = new Employee("Jesus",19,"male",1910);
        given(employeeRepositoryMongo.save(any()))
                .willReturn(employee1);
        //when
        Employee actual = employeeService.create(employee1);
        verify(employeeRepositoryMongo).save(employee1);
        //return
        assertEquals(employee1,actual);
    }
    @Test
    void should_return_employees_when_find_by_page_and_pageSize_given_employees_and_page_and_pageSize() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee1 = new Employee("Kobe",8,"male",1000);
        Employee employee2 = new Employee("Bryant",24,"male",2000);
        employees.add(employee1);
        employees.add(employee2);
        given(employeeRepositoryMongo.findAll(PageRequest.of(1, 2)))
                .willReturn(new PageImpl<>(employees, PageRequest.of(1, 2), 2));

        //When
        List<Employee> actual = employeeService.findByPage(1,2);
        //then
        verify(employeeRepositoryMongo).findAll(PageRequest.of(1, 2));
        assertEquals(actual,employees);

    }
    @Test
    void should_return_employee_when_delete_given_employees_id() {
        //given
        Employee employee = new Employee("1", "God",999,"male",0,"1");
        given(employeeRepository.remove(any()))
                .willReturn(employee);
        //when
//        Employee actual = employeeService.remove(employee.getId());
        verify(employeeRepository).remove(employee.getId());
        //return
//        assertEquals(employee,actual);
    }
}