package com.tatp.restapi;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository(){
        employees.add(new Employee(1, "Jason", 18, "male", 5));
        employees.add(new Employee(2, "Jason", 18, "male", 5));
        employees.add(new Employee(3, "Jason", 18, "female", 5));
        employees.add(new Employee(4, "Jason", 18, "female", 5));
        employees.add(new Employee(5, "Jason", 18, "female", 5));
        employees.add(new Employee(6, "Jason", 18, "female", 5));
    }

    public List<Employee> findAll(){
        return employees;
    }

    public Employee findById(Integer id){
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
        employee.setId(employees.stream()
                .mapToInt(Employee::getId)
                .max()
                .orElse(0)+1);
        employees.add(employee);
        return employee;
    }

    public Employee save(Integer id, Employee updatedEmployee){
        Employee employee = findById(id);
        employees.remove(employee);
        employees.add(updatedEmployee);
        return updatedEmployee;
    }

    public void remove(Integer id){
        Employee employee = findById(id);
        employees.remove(employee);
    }
}