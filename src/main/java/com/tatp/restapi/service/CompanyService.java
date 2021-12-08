package com.tatp.restapi.service;

import com.tatp.restapi.entity.Company;
import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.repository.CompanyRepository;
import com.tatp.restapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository){
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;

    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
    public Company findById(Integer id) {
        Company company = companyRepository.findById(id);
        company.setEmployees(employeeRepository.getEmployeesByCompanyID(id));
        return company;
    }

    public List<Employee> findEmployeesByCompanyId(Integer id) {
        return employeeRepository.getEmployeesByCompanyID(id);
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
        return companyRepository.findByPage(page, pageSize);
    }

    public Company create(Company company) {
        return companyRepository.create(company);
    }

    public void remove(Integer id) {
        Company company = companyRepository.findById(id);
        companyRepository.remove(id);
    }

    public Company edit(Integer id, Company updatedCompany){
        Company company = companyRepository.findById(id);
        if (updatedCompany.getCompanyName() != null)
            company.setCompanyName(updatedCompany.getCompanyName());
        return companyRepository.save(id, company);
    }

}
