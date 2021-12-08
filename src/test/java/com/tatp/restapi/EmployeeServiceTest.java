package com.tatp.restapi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_all_employees_when_find_all_given_employees() {
        //given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Jason", 10, "male", 1000));
        given(employeeRepository.findAll())
                .willReturn(employees);

        //when
        List<Employee> actual = employeeService.findAll();

        //then
        assertEquals(employees, actual);
    }

    @Test
    void should_return_a_employee_when_get_employee_given_employee_id() {
        //given
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee(1, "Jason", 10, "male", 1000);
        employees.add(employee);
        given(employeeRepository.findById(any()))
                .willReturn(employee);
        //when
        Employee actual = employeeService.findById(employee.getId());

        //then
        assertEquals(employee, actual);
    }

    @Test
    void should_return_updated_employee_when_edit_employee_given_updated_employee() {
        //given
        Employee employee = new Employee(1, "Jason", 10, "male", 1000);
        Employee updatedEmployee = new Employee(1, "Jason", 10, "male", 2000);
        given(employeeRepository.findById(any()))
                .willReturn(employee);
        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());
        given(employeeRepository.save(any(), any(Employee.class)))
                .willReturn(employee);

        //when
        Employee actual = employeeService.edit(employee.getId(), updatedEmployee);

        //then
        assertEquals(employee, actual);
    }

    @Test
    void should_employee_when_get_given_id() {
        //given
        Employee employee = new Employee(1, "Jason", 10, "male", 1000);
        given(employeeRepository.findById(any()))
                .willReturn(employee);
        //when
        Employee actual = employeeService.findById(employee.getId());
        verify(employeeRepository).findById(employee.getId());

        //return
        assertEquals(employee,actual);
    }
}