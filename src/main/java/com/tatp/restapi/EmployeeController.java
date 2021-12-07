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
    public Employee getEmployeeById(@PathVariable Integer id) throws NoEmployeeFoundException {
        return employeeRepository.findById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(@RequestParam String gender){
        return employeeRepository.findByGender(gender);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesbByPage(@RequestParam int page, @RequestParam int pageSize){
        return employeeRepository.findByPage(page, pageSize);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.create(employee);
    }

    @PutMapping("/{id}")
    public Employee editEmployee(@PathVariable Integer id, @RequestBody Employee updatedEmployee) throws NoEmployeeFoundException {
        Employee employee = employeeRepository.findById(id);
        if (updatedEmployee.getAge() != null)
            employee.setAge(updatedEmployee.getAge());
        if (updatedEmployee.getSalary() != null)
            employee.setSalary(updatedEmployee.getSalary());
        return employeeRepository.save(id, employee);
    }


}