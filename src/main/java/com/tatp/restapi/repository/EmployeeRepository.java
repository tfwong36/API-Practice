package com.tatp.restapi.repository;

import com.tatp.restapi.exception.NoEmployeeFoundException;
import com.tatp.restapi.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository(){
        employees.add(new Employee("1", "Jason1", 18, "male", 5, "1"));
        employees.add(new Employee("2", "Jason2", 19, "male", 5, "1"));
        employees.add(new Employee("3", "Jason3", 20, "female", 5, "3"));
        employees.add(new Employee("4", "Jason4", 21, "female", 5, "1"));
        employees.add(new Employee("5", "Jason5", 22, "female", 5, "2"));
        employees.add(new Employee("6", "Jason6", 23, "male", 5, "2"));
        employees.add(new Employee("7", "Jason7", 24, "female", 5, "4"));
        employees.add(new Employee("8", "Jason8", 25, "male", 5, "5"));
    }

    public List<Employee> findAll(){
        return employees;
    }

    public List<Employee> getEmployeesByCompanyID(String companyID){
        return findAll().stream()
                .filter(employee -> employee.getCompanyID().equals(companyID))
                .collect(Collectors.toList());
    }
    public Employee findById(String id){
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(NoEmployeeFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public List<Employee> findByPage(Integer page, Integer pageSize){
        return employees.stream()
                .skip((long)page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee create(Employee employee) {
        employee.setId(String.valueOf(employees.stream().mapToInt(singleEmployee->Integer.parseInt(singleEmployee.getId())).max().orElse(0)+1));
        employees.add(employee);
        return employee;
    }

    public Employee save(String id, Employee updatedEmployee){
        Employee employee = findById(id);
        employees.remove(employee);
        employees.add(updatedEmployee);
        return updatedEmployee;
    }

    public Employee remove(String id){
        Employee employee = findById(id);
        employees.remove(employee);
        return employee;
    }

    public void clearAll() {
        employees.clear();
    }
}