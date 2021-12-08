package com.tatp.restapi;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee edit(Integer id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id);
        if (updatedEmployee.getAge() != null && updatedEmployee.getAge().equals(0)){
            employee.setAge(updatedEmployee.getAge());
        }
        if (updatedEmployee.getSalary() != null && updatedEmployee.getSalary().equals(0)) {
            employee.setSalary(updatedEmployee.getSalary());
        }
        return employeeRepository.save(id, employee);
    }

    public Employee findById(Integer id) {
        return employeeRepository.findById(id);
    }
}
