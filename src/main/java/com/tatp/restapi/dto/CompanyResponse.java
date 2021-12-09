package com.tatp.restapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyResponse {
    private String id;
    private String name;
    private List<EmployeeResponse> employeeResponseList;

    public CompanyResponse(String id, String name, List<EmployeeResponse> employeeResponseList) {
        this.id = id;
        this.name = name;
        this.employeeResponseList = employeeResponseList;
    }

    public CompanyResponse() {
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

    public List<EmployeeResponse> getEmployeeResponseList() {
        return employeeResponseList;
    }

    public void setEmployeeResponseList(List<EmployeeResponse> employeeResponseList) {
        this.employeeResponseList = employeeResponseList;
    }
}
