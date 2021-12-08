package com.tatp.restapi.controller;

import com.tatp.restapi.repository.CompanyRepository;
import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.entity.Company;
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

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Integer id){
        return companyRepository.findById(id);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getEmployeesFromCompanyById(@PathVariable Integer id){
        return companyRepository.findEmployeesByCompanyId(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompaniesByPage(@RequestParam int page, @RequestParam int pageSize){
        return companyRepository.findByPage(page-1, pageSize);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody Company company){
        return companyRepository.create(company);
    }

    @PutMapping("/{id}")
    public Company editCompany(@PathVariable Integer id, @RequestBody Company updatedCompany){
        Company company = companyRepository.findById(id);
        if (updatedCompany.getCompanyName() != null)
            company.setCompanyName(updatedCompany.getCompanyName());
        return companyRepository.save(id, company);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCompany(@PathVariable Integer id){
        Company company = companyRepository.findById(id);
        companyRepository.remove(id);
    }

}
