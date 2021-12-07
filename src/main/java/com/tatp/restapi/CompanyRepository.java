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
        companies.add(new Company(5,"spring", Arrays.asList(new Employee(1, "Jason", 18, "male", 5))));
    }

    public List<Company> findAll(){
        return companies;
    }
}