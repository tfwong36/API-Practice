package com.tatp.restapi.entity;

import java.util.List;

public class Company {
    private String id;
    private String name;
    private List<Employee> employees;

    public Company(String id, String name, List<Employee> employess) {
        this.id = id;
        this.name = name;
        this.employees = employess;
    }
    public Company(String name) {
        this.name = name;
    }
    public Company(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
