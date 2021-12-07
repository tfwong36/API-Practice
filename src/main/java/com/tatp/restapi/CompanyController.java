package com.tatp.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {
    private CompanyRepository companyRepository;
    public CompanyController(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    @GetMapping()
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

//    @GetMapping()
//    public List<Employee> getCompanyById(){
//
//    }
//
//    @GetMapping(params = {"page", "pageSize"})
//    public List<Employee> getCompaniesByPage(@RequestParam int page, @RequestParam int pageSize){
//    }
//
//    @PostMapping
//    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
//    public Employee createCompany(@RequestBody Company company){
//
//    }
//
//    @PutMapping("/{id}")
//    public Employee editEmployee(@PathVariable Integer id, @RequestBody Employee updatedEmployee) throws NoCompanyFoundException {
//        Employee employee = noCompanyFoundException.findById(id);
//        if (updatedEmployee.getAge() != null)
//            employee.setAge(updatedEmployee.getAge());
//        if (updatedEmployee.getSalary() != null)
//            employee.setSalary(updatedEmployee.getSalary());
//        return noCompanyFoundException.save(id, employee);
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public Employee removeEmployee(@PathVariable Integer id) throws NoEmployeeFoundException {
//        Employee employee = employeeRepository.findById(id);
//        return employeeRepository.remove(id);
//    }

}
