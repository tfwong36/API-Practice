package com.tatp.restapi.service;

import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.exception.NoEmployeeFoundException;
import com.tatp.restapi.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        employees.add(new Employee("Jason", 10, "male", 1000));
        given(employeeRepository.findAll())
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
        given(employeeRepository.findById(any()))
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
        given(employeeRepository.findById(any()))
                .willReturn(java.util.Optional.of(employee));
        employee.setAge(updatedEmployee.getAge());
        employee.setSalary(updatedEmployee.getSalary());
        given(employeeRepository.save(any(Employee.class)))
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
        given(employeeRepository.findById(employee.getId()))
                .willReturn(java.util.Optional.of(employee));
        //when
        Employee actual = employeeService.findById(any());
        verify(employeeRepository).findById(employee.getId());

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
        given(employeeRepository.findByGender("male"))
                .willReturn(employees);
        //when
        List<Employee> actual = employeeService.findByGender("male");
        verify(employeeRepository).findByGender("male");
        //return
        assertEquals(employees,actual);
    }
    @Test
    void should_return_created_employee_when_create_given_employee() {
        //given
        Employee employee1 = new Employee("Jesus",19,"male",1910);
        given(employeeRepository.save(any()))
                .willReturn(employee1);
        //when
        Employee actual = employeeService.create(employee1);
        verify(employeeRepository).save(employee1);
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
        given(employeeRepository.findAll(PageRequest.of(1, 2)))
                .willReturn(new PageImpl<>(employees, PageRequest.of(1, 2), 2));

        //When
        List<Employee> actual = employeeService.findByPage(1,2);
        //then
        verify(employeeRepository).findAll(PageRequest.of(1, 2));
        assertEquals(actual,employees);

    }

    @Test
    void should_return_employee_when_delete_given_employees_id() {
        //given
        Employee employee = new Employee("God",999,"male",0);
        //When
        employeeService.remove(employee.getId());
        //then
        verify(employeeRepository).deleteById(employee.getId());

    }

    @Test
    void should_throw_exception_when_getEmployeeByID_given_employees_and_invalid_id() {
        //given
        Employee employee = new Employee("people",18, "male", 10);
        //when
        given(employeeRepository.findById("-1"))
                .willThrow(NoEmployeeFoundException.class);

        //then
        assertThrows(NoEmployeeFoundException.class, () -> employeeService.findById("-1"));
    }
}