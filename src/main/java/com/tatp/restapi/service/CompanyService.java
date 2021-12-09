package com.tatp.restapi.service;

import com.tatp.restapi.entity.Company;
import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.exception.NoCompanyFoundException;
import com.tatp.restapi.repository.CompanyRepository;
import com.tatp.restapi.repository.CompanyRepositoryMongo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;
    private EmployeeService employeeService;
    private CompanyRepositoryMongo companyRepositoryMongo;

    public CompanyService(CompanyRepository companyRepository, EmployeeService employeeService, CompanyRepositoryMongo companyRepositoryMongo) {
        this.companyRepository = companyRepository;
        this.employeeService = employeeService;
        this.companyRepositoryMongo = companyRepositoryMongo;
    }

    public List<Company> findAll() {
        return companyRepositoryMongo.findAll();
//        return companyRepository.findAll();
    }
    public Company findById(String id) {
        return companyRepositoryMongo.findById(id).orElseThrow(NoCompanyFoundException::new);
//        Company company = companyRepository.findById(id);
//        return company;
    }

    public List<Employee> findEmployeesByCompanyId(String id) {
        return employeeService.getEmployeesByCompanyID(id);
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
        return companyRepositoryMongo.findAll().stream()
                .skip((long)page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
//        return companyRepository.findByPage(page, pageSize);
    }

    public Company create(Company company) {
        return companyRepositoryMongo.save(company);
//        return companyRepository.create(company);
    }

    public void remove(String id) {
        companyRepositoryMongo.deleteById(id);
//        Company company = companyRepository.findById(id);
//        companyRepository.remove(id);
    }

    public Company edit(String id, Company updatedCompany){
        Company company = companyRepositoryMongo.findById(id).orElseThrow(NoCompanyFoundException::new);
//        Company company = companyRepository.findById(id);
        if (updatedCompany.getName() != null)
            company.setName(updatedCompany.getName());
        return companyRepositoryMongo.save(company);
//        return companyRepository.save(id, company);
    }

}
