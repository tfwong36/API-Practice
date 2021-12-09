package com.tatp.restapi.service;

import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.exception.NoEmployeeFoundException;
import com.tatp.restapi.repository.CompanyRepository;
import com.tatp.restapi.repository.EmployeeRepository;
import com.tatp.restapi.repository.EmployeeRepositoryMongo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;
    private CompanyRepository companyRepository;
    private EmployeeRepositoryMongo employeeRepositoryMongo;

    public EmployeeService(EmployeeRepository employeeRepository, CompanyRepository companyRepository, EmployeeRepositoryMongo employeeRepositoryMongo) {
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
        this.employeeRepositoryMongo = employeeRepositoryMongo;
    }

    public List<Employee> findAll() {
        return employeeRepositoryMongo.findAll();
//        return employeeRepository.findAll();
    }

    public Employee edit(String id, Employee updatedEmployee) {
        Employee employee = employeeRepositoryMongo.findById(id).orElseThrow(NoEmployeeFoundException::new);
        //Employee employee = employeeRepository.findById(id);
        if (updatedEmployee.getAge() != null && !updatedEmployee.getAge().equals(0))
            employee.setAge(updatedEmployee.getAge());
        if (updatedEmployee.getSalary() != null && !updatedEmployee.getSalary().equals(0))
            employee.setSalary(updatedEmployee.getSalary());
        return employeeRepositoryMongo.save(employee);
//        return employeeRepository.save(id, employee);
    }

    public Employee findById(String id) {
//        return employeeRepositoryMongo.findById(id).orElseThrow(NoEmployeeFoundException::new);
        return employeeRepository.findById(id);
    }

    public List<Employee> findByGender(String gender) {
        return employeeRepositoryMongo.findByGender(gender);
//        return employeeRepository.findByGender(gender);
    }

    public Employee create(Employee employee) {
        return employeeRepositoryMongo.save(employee);
//        return employeeRepository.create(employee);
    }

    public List<Employee> findByPage(Integer page, Integer pageSize) {
        return employeeRepositoryMongo.findAll().stream()
                .skip((long)page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
//        return employeeRepository.findByPage(page, pageSize);
    }

    public void remove(String id) {
        employeeRepositoryMongo.deleteById(id);
//        return employeeRepository.remove(id);
    }

    public List<Employee> getEmployeesByCompanyID(String companyID){
        return employeeRepositoryMongo.findByCompanyID(companyID);
    }
}
