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

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Integer id) throws NoCompanyFoundException, NoCompanyFoundException {
        return companyRepository.findById(id);
    }

    @GetMapping("/employees")
    public List<Employee> getEmployeesFromCompanyById(@PathVariable Integer id) throws NoCompanyFoundException, NoCompanyFoundException {
        return companyRepository.findEmployeesByCompanyId(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getCompaniesByPage(@RequestParam int page, @RequestParam int pageSize){
        return companyRepository.findByPage(page-1, pageSize);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED, reason = "CREATED")
    public Company createCompany(@RequestBody Company company){
        return companyRepository.create(company);
    }

    @PutMapping("/{id}")
    public Company editCompany(@PathVariable Integer id, @RequestBody Company updatedCompany) throws NoCompanyFoundException {
        Company company = companyRepository.findById(id);
        if (updatedCompany.getEmployees() != null)
            company.setEmployees(updatedCompany.getEmployees());
        if (updatedCompany.getCompanyName() != null)
            company.setCompanyName(updatedCompany.getCompanyName());
        return companyRepository.save(id, company);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Company removeCompany(@PathVariable Integer id) throws NoCompanyFoundException {
        Company company = companyRepository.findById(id);
        return companyRepository.remove(id);
    }

}
