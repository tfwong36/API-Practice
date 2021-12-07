package com.tatp.restapi;

import java.util.ArrayList;
import java.util.List;

public class Company {
    private Integer id;
    private String companyName;
    private List<Employee> employees;

    public Company(Integer id, String companyName, List<Employee> employess) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employess;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }
}