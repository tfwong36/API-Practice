package com.tatp.restapi;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;
    public EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id){
        return employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(@RequestParam String gender){
        return employeeRepository.findByGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesByPage(@RequestParam int page, @RequestParam int pageSize){
        return employeeRepository.findByPage(page-1, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.create(employee);
    }

    @PutMapping("/{id}") //NOT PATCH?
    public Employee editEmployee(@PathVariable Integer id, @RequestBody Employee updatedEmployee){
        Employee employee = employeeRepository.findById(id);
        if (updatedEmployee.getAge() != null && !updatedEmployee.getAge().equals(0))
            employee.setAge(updatedEmployee.getAge());
        if (updatedEmployee.getSalary() != null && !updatedEmployee.getSalary().equals(0))
            employee.setSalary(updatedEmployee.getSalary());
        return employeeRepository.save(id, employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployee(@PathVariable Integer id){
        Employee employee = employeeRepository.findById(id);
        employeeRepository.remove(id);
    }


}