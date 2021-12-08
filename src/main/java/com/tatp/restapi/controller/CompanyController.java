package com.tatp.restapi.controller;

import com.tatp.restapi.repository.CompanyRepository;
import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.entity.Company;
import com.tatp.restapi.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {
    private CompanyService companyService;
    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @GetMapping()
    public List<Company> getAllCompanies(){
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Integer id){
        return companyService.findById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesFromCompanyById(@PathVariable Integer id){
        return companyService.findEmployeesByCompanyId(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompaniesByPage(@RequestParam int page, @RequestParam int pageSize){
        return companyService.findByPage(page-1, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company){
        return companyService.create(company);
    }

    @PutMapping("/{id}")
    public Company editCompany(@PathVariable Integer id, @RequestBody Company updatedCompany){
        return companyService.edit(id, updatedCompany);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCompany(@PathVariable Integer id){
        companyService.remove(id);
    }

}
