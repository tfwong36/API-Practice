package com.tatp.restapi.service;

import com.tatp.restapi.entity.Company;
import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.repository.CompanyRepository;
import com.tatp.restapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

@Service
public class CompanyService {
    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository){
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
    public Company findById(Integer id) {
        return companyRepository.findById(id);
    }

    public List<Employee> findEmployeesByCompanyId(Integer id) {
        return companyRepository.findEmployeesByCompanyId(id);
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
