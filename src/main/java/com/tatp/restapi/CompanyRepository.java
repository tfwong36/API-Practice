package com.tatp.restapi;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public CompanyRepository(){
        companies.add(new Company(1,"spring", Arrays.asList(new Employee(1, "Jason", 18, "male", 5))));
        companies.add(new Company(2,"springboot", Arrays.asList(new Employee(1, "Jason", 18, "male", 5))));
    }

    public List<Company> findAll(){
        return companies;
    }

    public Company findById(Integer id) throws NoCompanyFoundException {
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
}