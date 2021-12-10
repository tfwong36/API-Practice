package com.tatp.restapi.service;

import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.exception.NoCompanyFoundException;
import com.tatp.restapi.exception.NoEmployeeFoundException;
import com.tatp.restapi.repository.EmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee edit(String id, Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id).orElseThrow(NoEmployeeFoundException::new);
        if (updatedEmployee.getAge() != null && !updatedEmployee.getAge().equals(0))
            employee.setAge(updatedEmployee.getAge());
        if (updatedEmployee.getSalary() != null && !updatedEmployee.getSalary().equals(0))
            employee.setSalary(updatedEmployee.getSalary());
        return employeeRepository.save(employee);
    }

    public Employee findById(String id) {
        return employeeRepository.findById(id).orElseThrow(NoEmployeeFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> findByPage(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Employee remove(String id) {
        employeeRepository.findById(id).orElseThrow(NoEmployeeFoundException::new);
        employeeRepository.deleteById(id);
        return null;
    }

    public List<Employee> getEmployeesByCompanyID(String companyID){
        return employeeRepository.findByCompanyID(companyID);
    }
}
