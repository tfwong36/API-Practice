package com.tatp.restapi.controller;
import com.tatp.restapi.dto.EmployeeRequest;
import com.tatp.restapi.dto.EmployeeResponse;
import com.tatp.restapi.mapper.EmployeeMapper;
import com.tatp.restapi.repository.EmployeeRepository;
import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    private EmployeeService employeeService;
    private EmployeeMapper employeeMapper = new EmployeeMapper();
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees(){
        return employeeService.findAll().stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable String id){
        return employeeMapper.toResponse(employeeService.findById(id));
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getEmployeesByGender(@RequestParam String gender){
        return employeeService.findByGender(gender).stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getEmployeesByPage(@RequestParam int page, @RequestParam int pageSize){
        return employeeService.findByPage(page-1, pageSize).stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createEmployee(@RequestBody EmployeeRequest employeeRequest){
        return employeeMapper.toResponse(employeeService.create(employeeMapper.toEntity(employeeRequest)));
    }

    @PutMapping("/{id}") //NOT PATCH?
    public EmployeeResponse editEmployee(@PathVariable String id, @RequestBody EmployeeRequest employeeRequest){
        return employeeMapper.toResponse(employeeService.edit(id, employeeMapper.toEntity(employeeRequest)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeEmployee(@PathVariable String id){
        employeeService.remove(id);
    }


}