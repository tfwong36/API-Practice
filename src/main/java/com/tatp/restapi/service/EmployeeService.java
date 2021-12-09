package com.tatp.restapi.service;

import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.exception.NoEmployeeFoundException;
import com.tatp.restapi.repository.EmployeeRepository;
import com.tatp.restapi.repository.EmployeeRepositoryMongo;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeRepositoryMongo employeeRepositoryMongo;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeRepositoryMongo employeeRepositoryMongo) {
        this.employeeRepository = employeeRepository;
        this.employeeRepositoryMongo = employeeRepositoryMongo;
    }

    public List<Employee> findAll() {
        return employeeRepositoryMongo.findAll();
    }

    public Employee edit(String id, Employee updatedEmployee) {
        Employee employee = employeeRepositoryMongo.findById(id).orElseThrow(NoEmployeeFoundException::new);
        if (updatedEmployee.getAge() != null && !updatedEmployee.getAge().equals(0))
            employee.setAge(updatedEmployee.getAge());
        if (updatedEmployee.getSalary() != null && !updatedEmployee.getSalary().equals(0))
            employee.setSalary(updatedEmployee.getSalary());
        return employeeRepositoryMongo.save(employee);
    }

    public Employee findById(String id) {
        return employeeRepositoryMongo.findById(id).orElseThrow(NoEmployeeFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepositoryMongo.findByGender(gender);
    }

    public Employee create(Employee employee) {
        return employeeRepositoryMongo.save(employee);
    }

    public List<Employee> findByPage(Integer page, Integer pageSize) {
        return employeeRepositoryMongo.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Employee remove(String id) {
        employeeRepositoryMongo.deleteById(id);
        return null;
    }

    public List<Employee> getEmployeesByCompanyID(String companyID){
        return employeeRepositoryMongo.findByCompanyID(companyID);
    }
}
