package com.tatp.restapi.service;

import com.tatp.restapi.entity.Company;
import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.exception.NoCompanyFoundException;
import com.tatp.restapi.repository.CompanyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private EmployeeService employeeService;
    private CompanyRepository companyRepository;

    public CompanyService(EmployeeService employeeService, CompanyRepository companyRepository) {
        this.employeeService = employeeService;
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
    public Company findById(String id) {
        return companyRepository.findById(id).orElseThrow(NoCompanyFoundException::new);
    }

    public List<Employee> findEmployeesByCompanyId(String id) {
        return employeeService.getEmployeesByCompanyID(id);
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    public Company create(Company company) {
        return companyRepository.save(company);
    }

    public void remove(String id) {
        companyRepository.findById(id).orElseThrow(NoCompanyFoundException::new);
        companyRepository.deleteById(id);
    }

    public Company edit(String id, Company updatedCompany){
        Company company = companyRepository.findById(id).orElseThrow(NoCompanyFoundException::new);
        if (updatedCompany.getName() != null)
            company.setName(updatedCompany.getName());
        return companyRepository.save(company);
    }

}
