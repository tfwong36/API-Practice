package com.tatp.restapi.repository;

import com.tatp.restapi.entity.Employee;
import com.tatp.restapi.exception.NoCompanyFoundException;
import com.tatp.restapi.entity.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public CompanyRepository(){
        companies.add(new Company("1","Spring", null));
        companies.add(new Company("2","SpringBoot", null));
        companies.add(new Company("3","Flask", null));
        companies.add(new Company("4","Tomcat", null));
        companies.add(new Company("5","JerryFish", null));
    }

    public void clearAll() {
        companies.clear();
    }

    public List<Company> findAll(){
        return companies;
    }

    public Company findById(String id){
        Company foundCompany = companies.stream()
                            .filter(company -> company.getId().equals(id))
                            .findFirst()
                            .orElseThrow(NoCompanyFoundException::new);
        return foundCompany;
    }

    public List<Company> findByPage(int page, int pageSize) {
        return companies.stream()
                .skip((long)page * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Company create(Company company) {
        company.setId(String.valueOf(companies.stream().mapToInt(singleCompany->Integer.parseInt(singleCompany.getId())).max().orElse(0)+1));
        companies.add(company);
        return company;
    }

    public Company save(String id, Company updatedCompany){
        Company company = findById(id);
        companies.remove(company);
        companies.add(updatedCompany);
        return updatedCompany;
    }

    public void remove(String id) {
        Company company = findById(id);
        companies.remove(company);
    }

    public List<Employee> findEmployeesByCompanyId(Integer id){
        //never reach here
        return null;
    }
}