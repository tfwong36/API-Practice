package com.tatp.restapi.mapper;

import com.tatp.restapi.dto.EmployeeRequest;
import com.tatp.restapi.dto.EmployeeResponse;
import com.tatp.restapi.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public Employee toEntity(EmployeeRequest employeeRequest){
        Employee employee = new Employee();
        //M1 manual set all attribute to employee from employeeRequest
//        employee.setAge(employeeRequest.getAge());
//        employee.setSalary(employeeRequest.getSalary());
//        employee.setGender(employeeRequest.getGender());
//        employee.setCompanyID(employeeRequest.getCompanyID());

        //M2 BeanUtils
        BeanUtils.copyProperties(employeeRequest, employee);
        return employee;
    }

    public EmployeeResponse toResponse(Employee employee){
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
    }
}
