package com.tatp.restapi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company {
    private String id;
    private String name;
    private List<Employee> employees;

    public Company(String id, String name, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
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
