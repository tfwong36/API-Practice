package com.tatp.restapi;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public CompanyRepository(){
        companies.add(new Company(1,"Spring", new EmployeeRepository().findAll()));
        companies.add(new Company(2,"SpringBoot", new EmployeeRepository().findAll()));
        companies.add(new Company(3,"Flask", new EmployeeRepository().findAll()));
        companies.add(new Company(4,"Tomcat", new EmployeeRepository().findAll()));
        companies.add(new Company(5,"JerryFish", new EmployeeRepository().findAll()));
    }

    public List<Company> findAll(){
        return companies;
    }

    public Company findById(Integer id){
        return companies.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(NoCompanyFoundException::new);
    }

    public List<Company> findByPage(int page, int pageSize) {
        return companies.stream()
                .skip((long)page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company create(Company company) {
        company.setId(companies.stream()
                .mapToInt(Company::getId)
                .max()
                .orElse(0)+1);
        companies.add(company);
        return company;
    }

    public Company save(Integer id, Company updatedCompany){
        Company company = findById(id);
        companies.remove(company);
        companies.add(updatedCompany);
        return updatedCompany;
    }

    public void remove(Integer id) {
        Company company = findById(id);
        companies.remove(company);
    }

    public List<Employee> findEmployeesByCompanyId(Integer id){
        Company company =  companies.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(NoCompanyFoundException::new);
        return company.getEmployees();

    }
}