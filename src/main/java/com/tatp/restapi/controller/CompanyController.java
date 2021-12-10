package com.tatp.restapi.controller;

import com.tatp.restapi.dto.CompanyRequest;
import com.tatp.restapi.dto.CompanyResponse;
import com.tatp.restapi.dto.EmployeeResponse;
import com.tatp.restapi.entity.Company;
import com.tatp.restapi.mapper.CompanyMapper;
import com.tatp.restapi.mapper.EmployeeMapper;
import com.tatp.restapi.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("companies")
public class CompanyController {
    private CompanyMapper companyMapper = new CompanyMapper();
    private EmployeeMapper employeeMapper = new EmployeeMapper();
    private CompanyService companyService;
    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @GetMapping()
    public List<CompanyResponse> getAllCompanies(){
        return companyService.findAll().stream()
                .map(company -> companyMapper.toResponse(company, null))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyResponse getCompanyById(@PathVariable String id){
        Company company = companyService.findById(id);
        List<EmployeeResponse> employees = companyService.findEmployeesByCompanyId(id).stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
        return companyMapper.toResponse(companyService.findById(id), employees);
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponse> getEmployeesFromCompanyById(@PathVariable String id){
        return companyService.findEmployeesByCompanyId(id).stream()
                .map(employee -> employeeMapper.toResponse(employee))
                .collect(Collectors.toList());
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getCompaniesByPage(@RequestParam int page, @RequestParam int pageSize){
        return companyService.findByPage(page-1, pageSize).stream()
                .map(company -> companyMapper.toResponse(company, null))
                .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createCompany(@RequestBody CompanyRequest companyRequest){
        return companyService.create(companyMapper.toEntity(companyRequest));
    }

    @PutMapping("/{id}")
    public Company editCompany(@PathVariable String id, @RequestBody CompanyRequest updatedCompany){
        return companyService.edit(id, companyMapper.toEntity(updatedCompany));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCompany(@PathVariable String id){
        companyService.remove(id);
    }

}
